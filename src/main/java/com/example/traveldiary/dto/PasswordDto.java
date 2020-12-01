package com.example.traveldiary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordDto {

    @Schema(
            description = "old password",
            example = "AbC1*3",
            required = true)
    private String oldPassword;

    @Schema(
            description = "new password",
            example = "dEf_5+",
            required = true)
    private String password;

    @Schema(
            description = "matching password",
            example = "dEf_5+",
            required = true)
    private String matchingPassword;

}
