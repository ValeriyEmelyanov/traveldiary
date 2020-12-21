package com.example.traveldiary.dto.request;

import com.example.traveldiary.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDto {

    @Schema(description = "user name",
            example = "alex",
            required = true)
    private final String username;

    @Schema(description = "user password",
            example = "dEf_5+",
            required = true)
    private final String password;

    @Schema(description = "determines the user's ability to work in the service",
            example = "true",
            required = true)
    private final Boolean enabled;

    @Schema(description = "user roles",
            example = "[\"SENIOR\", \"USER\"]",
            required = true)
    private final Set<Role> roles;
}
