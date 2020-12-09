package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.PasswordDto;
import com.example.traveldiary.dto.UserDto;
import com.example.traveldiary.exception.BadPasswordException;
import com.example.traveldiary.exception.BadRequestException;
import com.example.traveldiary.exception.ForbiddenException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.mapper.UserMapper;
import com.example.traveldiary.model.Permission;
import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.UserRepositiry;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepositiry userRepositiry;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepositiry userRepositiry,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.userRepositiry = userRepositiry;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
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
        save(null, userDto, false);
    }

    @Override
    public void update(Long id, UserDto userDto) {
        if (id == null) {
            throw new BadRequestException();
        }
        save(id, userDto, true);
    }

    private void save(Long id, UserDto userDto, boolean isUpdate) {
        if (userDto == null) {
            throw new BadRequestException();
        }

        User user;
        if (isUpdate) {
            user = getById(id);
            userMapper.updateUser(userDto, user);
        } else {
            user = userMapper.toUser(userDto);
            user.setCreated(LocalDateTime.now());
        }

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepositiry.save(user);
    }

    @Override
    public void changePassword(
            String username,
            Collection<? extends GrantedAuthority> authorities,
            Long id,
            PasswordDto passwordDto) {

        if (passwordDto == null || passwordDto.getPassword() == null) {
            throw new BadRequestException();
        }

        User currentUser = getByUsername(username);
        User user = getById(id);
        if (!currentUser.equals(user)) {
            boolean hasPermissionUserWrite = authorities.stream()
                    .anyMatch(grantedAuthority
                            -> grantedAuthority.getAuthority().equals(Permission.USER_WRITE.getPermission()));
            if (!hasPermissionUserWrite) {
                throw new ForbiddenException();
            }
        } else if (passwordDto.getOldPassword() == null
                || !passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
            throw new ForbiddenException();
        }

        if (!passwordDto.getPassword().equals(passwordDto.getMatchingPassword())) {
            throw new BadPasswordException("Password not matching");
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
