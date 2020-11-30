package com.example.traveldiary.dto;

import com.example.traveldiary.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserDto {

    @Schema(
            description = "user name",
            example = "alex",
            required = true)
    private String username;

    @Schema(
            description = "user password",
            example = "dEf_5+",
            required = true)
    private String password;

    @Schema(
            description = "determines the user's ability to work in the service",
            example = "true",
            required = true)
    private Boolean enabled;

    @Schema(
            description = "user roles",
            example = "['SENIOR', 'USER']",
            required = true)
    private Set<Role> roles;

}
