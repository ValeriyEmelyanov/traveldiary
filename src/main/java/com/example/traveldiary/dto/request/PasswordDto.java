package com.example.traveldiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordDto {

    @Schema(description = "old password",
            example = "AbC1*3",
            required = true)
    private final String oldPassword;

    @Schema(description = "new password",
            example = "dEf_5+",
            required = true)
    private final String password;

    @Schema(description = "matching password",
            example = "dEf_5+",
            required = true)
    private final String matchingPassword;
}
