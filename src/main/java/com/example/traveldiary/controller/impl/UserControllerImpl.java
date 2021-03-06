package com.example.traveldiary.controller.impl;

import com.example.traveldiary.controller.UserController;
import com.example.traveldiary.dto.intermediate.PasswordDto;
import com.example.traveldiary.dto.request.PasswordRequest;
import com.example.traveldiary.dto.request.UserRequest;
import com.example.traveldiary.dto.response.UserResponse;
import com.example.traveldiary.model.User;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final ConversionService conversionService;

    @Autowired
    public UserControllerImpl(UserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @Override
    public ResponseEntity<List<UserResponse>> getList() {
        return ResponseEntity.ok(userService.getList()
                .stream()
                .map(e -> conversionService.convert(e, UserResponse.class))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<UserResponse> getById(Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(Objects.requireNonNull(
                conversionService.convert(user, UserResponse.class)));
    }

    @Override
    public ResponseEntity<UserResponse> create(UserRequest userRequest) {
        User saved = userService.save(
                Objects.requireNonNull(conversionService.convert(userRequest, User.class)));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(conversionService.convert(saved, UserResponse.class));
    }

    @Override
    public ResponseEntity<UserResponse> update(Long id,
                                               UserRequest userRequest) {
        User updated = userService.update(id,
                Objects.requireNonNull(conversionService.convert(userRequest, User.class)));
        return ResponseEntity.ok(Objects.requireNonNull(
                conversionService.convert(updated, UserResponse.class)));
    }

    @Override
    public ResponseEntity<String> changePassword(Long id,
                                                 PasswordRequest passwordRequest,
                                                 Authentication authentication) {
        userService.changePassword(
                ((UserDetails) authentication.getPrincipal()).getUsername(),
                authentication.getAuthorities(),
                id,
                Objects.requireNonNull(conversionService.convert(passwordRequest, PasswordDto.class)));

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
