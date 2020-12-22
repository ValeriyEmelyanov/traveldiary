package com.example.traveldiary.service.impl;

import com.example.traveldiary.exception.ErrorMessages;
import com.example.traveldiary.exception.BadLoginPasswordException;
import com.example.traveldiary.model.User;
import com.example.traveldiary.security.JwtProvider;
import com.example.traveldiary.service.AuthService;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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
    public String auth(String username, String password) {
        User user;
        try {
            user = userService.getByUsername(username);
        } catch (Exception e) {
            throw new BadLoginPasswordException(ErrorMessages.BAD_LOGIN_PASSWORD.getErrorMessage());
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadLoginPasswordException(ErrorMessages.BAD_LOGIN_PASSWORD.getErrorMessage());
        }

        return jwtProvider.generateToken(username);
    }
}
