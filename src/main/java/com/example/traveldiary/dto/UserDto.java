package com.example.traveldiary.dto;

import com.example.traveldiary.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserDto {
    private String username;
    private String password;
    private Boolean enabled;
    private Set<Role> roles;
}
