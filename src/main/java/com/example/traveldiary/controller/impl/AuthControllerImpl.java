package com.example.traveldiary.controller.impl;

import com.example.traveldiary.controller.AuthController;
import com.example.traveldiary.dto.request.UserLoginRequest;
import com.example.traveldiary.dto.response.AuthResponse;
import com.example.traveldiary.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }


    @Override
    public ResponseEntity<AuthResponse> auth(UserLoginRequest loginRequest) {
        return ResponseEntity.ok(
                new AuthResponse(authService.auth(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())));
    }
}
