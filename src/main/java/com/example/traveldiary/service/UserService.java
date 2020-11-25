package com.example.traveldiary.service;


import com.example.traveldiary.dto.PasswordDto;
import com.example.traveldiary.dto.UserDto;
import com.example.traveldiary.model.User;

import java.util.List;

public interface UserService {
    List<User> getList();

    User getById(Long id);

    User getByUsername(String username);

    void save(UserDto userDto);

    void update(Long id, UserDto userDto);

    void changePassword(String username, PasswordDto passwordDto);

    void delete(Long id);
}
