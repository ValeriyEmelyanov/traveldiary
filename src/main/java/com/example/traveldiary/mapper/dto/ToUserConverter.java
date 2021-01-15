package com.example.traveldiary.mapper.dto;

import com.example.traveldiary.dto.request.UserRequest;
import com.example.traveldiary.model.Role;
import com.example.traveldiary.model.User;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

public class ToUserConverter implements Converter<UserRequest, User> {
    @Override
    public User convert(UserRequest userRequest) {
        User user = new User();

        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEnabled(userRequest.getEnabled());

        Set<Role> set = userRequest.getRoles();
        if (set != null) {
            user.setRoles(new HashSet<>(set));
        }

        return user;
    }
}
