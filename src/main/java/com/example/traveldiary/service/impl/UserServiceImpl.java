package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.PasswordDto;
import com.example.traveldiary.dto.UserDto;
import com.example.traveldiary.exception.BadPasswordException;
import com.example.traveldiary.exception.BadRequestException;
import com.example.traveldiary.exception.ForbiddenException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.UserRepositiry;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepositiry userRepositiry;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepositiry userRepositiry, PasswordEncoder passwordEncoder) {
        this.userRepositiry = userRepositiry;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getList() {
        return userRepositiry.findAll();
    }

    @Override
    public User getById(Long id) {
        if (id == null) {
            throw new BadRequestException();
        }
        return userRepositiry.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public User getByUsername(String username) {
        if (username == null) {
            throw new BadRequestException();
        }
        return userRepositiry.findByUsername(username).orElseThrow(NotFoundException::new);
    }

    @Override
    public void save(UserDto userDto) {
        save(userDto, false);
    }

    @Override
    public void update(UserDto userDto) {
        save(userDto, true);
    }

    private void save(UserDto userDto, boolean isUpdate) {
        if (userDto == null) {
            throw new BadRequestException();
        }

        User user = null;
        if (isUpdate) {
            user = getById(userDto.getId());
        } else {
            user = new User();
            user.setCreated(LocalDateTime.now());
        }
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(userDto.getEnabled());
        user.setRoles(userDto.getRoles());

        userRepositiry.save(user);
    }

    @Override
    public void changePassword(String username, PasswordDto passwordDto) {
        if (passwordDto == null || passwordDto.getPassword() == null) {
            throw new BadRequestException();
        }
        if (!passwordDto.getPassword().equals(passwordDto.getMatchingPassword())) {
            throw new BadPasswordException("Password not matching");
        }

        User user = getByUsername(username);
        if (passwordDto.getOldPassword() == null
                || !passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
            throw new ForbiddenException();
        }

        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));

        userRepositiry.save(user);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        userRepositiry.deleteById(id);
    }
}
