package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.request.PasswordDto;
import com.example.traveldiary.dto.request.UserDto;
import com.example.traveldiary.dto.response.ErrorMessages;
import com.example.traveldiary.exception.BadPasswordException;
import com.example.traveldiary.exception.BadRequestException;
import com.example.traveldiary.exception.ForbiddenException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.mapper.UserMapper;
import com.example.traveldiary.model.Permission;
import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.UserRepository;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepositiry;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepositiry,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.userRepositiry = userRepositiry;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getList() {
        return userRepositiry.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User getById(Long id) {
        if (id == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }
        return userRepositiry.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND.getErrorMessage()));
    }

    @Transactional(readOnly = true)
    @Override
    public User getByUsername(String username) {
        if (username == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }
        return userRepositiry.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND.getErrorMessage()));
    }

    @Transactional
    @Override
    public void save(UserDto userDto) {
        save(null, userDto, false);
    }

    @Transactional
    @Override
    public void update(Long id, UserDto userDto) {
        if (id == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }
        save(id, userDto, true);
    }

    private void save(Long id, UserDto userDto, boolean isUpdate) {
        if (userDto == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
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

    @Transactional
    @Override
    public void changePassword(
            String username,
            Collection<? extends GrantedAuthority> authorities,
            Long id,
            PasswordDto passwordDto) {

        if (passwordDto == null || passwordDto.getPassword() == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }

        User currentUser = getByUsername(username);
        User user = getById(id);
        if (!currentUser.equals(user)) {
            boolean hasPermissionUserWrite = authorities.stream()
                    .anyMatch(grantedAuthority
                            -> grantedAuthority.getAuthority().equals(Permission.USER_WRITE.getPermission()));
            if (!hasPermissionUserWrite) {
                throw new ForbiddenException(ErrorMessages.NO_PERMISSIONS.getErrorMessage());
            }
        } else if (passwordDto.getOldPassword() == null
                || !passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
            throw new ForbiddenException(ErrorMessages.WRONG_OLD_PASSWORD.getErrorMessage());
        }

        if (!passwordDto.getPassword().equals(passwordDto.getMatchingPassword())) {
            throw new BadPasswordException(ErrorMessages.BAD_PASSWORD_NOT_MATCHING.getErrorMessage());
        }

        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));

        userRepositiry.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        getById(id);
        userRepositiry.deleteById(id);
    }
}
