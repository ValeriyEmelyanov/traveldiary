package com.example.traveldiary.service;


import com.example.traveldiary.dto.UserDto;
import com.example.traveldiary.model.User;

import java.util.List;

public interface UserService {
    List<User> getList();

    User getById(Long id);

    boolean notExists(Long id);

    User getByUsername(String username);

    void save(UserDto user);

    void delete(Long id);
}
