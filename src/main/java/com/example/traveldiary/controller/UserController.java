package com.example.traveldiary.controller;

import com.example.traveldiary.dto.UserDto;
import com.example.traveldiary.model.User;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<List<User>> getList() {
        return ResponseEntity.ok(userService.getList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        User user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<String> create(@RequestBody UserDto userDto) {
        if (userDto == null) {
            return ResponseEntity.badRequest().build();
        }

        userService.save(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<String> update(@RequestBody UserDto userDto) {
        if (userDto == null) {
            return ResponseEntity.badRequest().build();
        }

        if (userService.notExists(userDto.getId())) {
            return ResponseEntity.notFound().build();
        }

        userService.save(userDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        if (userService.notExists(id)) {
            return ResponseEntity.notFound().build();
        }

        userService.delete(id);

        return ResponseEntity.ok().build();
    }
}
