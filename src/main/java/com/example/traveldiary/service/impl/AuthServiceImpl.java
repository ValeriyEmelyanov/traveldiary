package com.example.traveldiary.service.impl;

import com.example.traveldiary.exception.BadLoginPasswordException;
import com.example.traveldiary.exception.ErrorMessages;
import com.example.traveldiary.model.User;
import com.example.traveldiary.security.JwtProvider;
import com.example.traveldiary.service.AuthService;
import com.example.traveldiary.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserService userService,
                           JwtProvider jwtProvider,
                           PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @NonNull
    public String auth(@NonNull String username, @NonNull String password) {
        Assert.notNull(username, ErrorMessages.NULL_USERNAME.getErrorMessage());
        Assert.notNull(password, ErrorMessages.NULL_PASSWORD.getErrorMessage());

        User user;
        try {
            user = userService.getByUsername(username);
        } catch (Exception e) {
            throw new BadLoginPasswordException(ErrorMessages.BAD_LOGIN_PASSWORD.getErrorMessage());
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadLoginPasswordException(ErrorMessages.BAD_LOGIN_PASSWORD.getErrorMessage());
        }

        log.info("Generated the token for {}", username);
        return jwtProvider.generateToken(username);
    }
}
