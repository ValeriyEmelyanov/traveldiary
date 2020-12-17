package com.example.traveldiary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
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
