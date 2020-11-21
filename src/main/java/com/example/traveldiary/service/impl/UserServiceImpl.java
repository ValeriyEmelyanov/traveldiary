package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.UserDto;
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
        return userRepositiry.findById(id).orElse(null);
    }

    @Override
    public boolean notExists(Long id) {
        return getById(id) == null;
    }

    @Override
    public User getByUsername(String username) {
        return userRepositiry.findByUsername(username).orElse(null);
    }

    @Override
    public void save(UserDto userDto) {
        User user = null;
        if (userDto.getId() != null) {
            user = getById(userDto.getId());
        }
        if (user == null) {
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
    public void delete(Long id) {
        userRepositiry.deleteById(id);
    }
}
