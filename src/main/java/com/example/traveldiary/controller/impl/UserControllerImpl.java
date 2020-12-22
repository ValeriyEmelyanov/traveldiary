package com.example.traveldiary.controller.impl;

import com.example.traveldiary.controller.UserController;
import com.example.traveldiary.dto.intermediate.PasswordData;
import com.example.traveldiary.dto.request.PasswordDto;
import com.example.traveldiary.dto.request.UserDto;
import com.example.traveldiary.model.User;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<List<User>> getList() {
        return ResponseEntity.ok(userService.getList());
    }

    @Override
    public ResponseEntity<User> getById(Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<String> create(UserDto userDto) {
        userService.save(conversionService.convert(userDto, User.class));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<String> update(Long id,
                                         UserDto userDto) {
        userService.update(id, conversionService.convert(userDto, User.class));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<String> changePassword(Long id,
                                                 PasswordDto passwordDto,
                                                 Authentication authentication) {
        userService.changePassword(
                ((UserDetails) authentication.getPrincipal()).getUsername(),
                authentication.getAuthorities(),
                id,
                conversionService.convert(passwordDto, PasswordData.class));

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
