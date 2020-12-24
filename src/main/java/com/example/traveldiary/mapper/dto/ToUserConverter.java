package com.example.traveldiary.mapper.dto;

import com.example.traveldiary.dto.request.UserDto;
import com.example.traveldiary.model.Role;
import com.example.traveldiary.model.User;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

public class ToUserConverter implements Converter<UserDto, User> {
    @Override
    public User convert(UserDto userDto) {
        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());

        Set<Role> set = userDto.getRoles();
        if (set != null) {
            user.setRoles(new HashSet<>(set));
        }

        return user;
    }
}
