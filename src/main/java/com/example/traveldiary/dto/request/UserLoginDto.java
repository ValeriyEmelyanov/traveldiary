package com.example.traveldiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginDto {

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

}
