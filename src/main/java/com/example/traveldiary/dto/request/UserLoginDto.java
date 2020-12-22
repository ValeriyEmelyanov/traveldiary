package com.example.traveldiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserLoginDto {

    @Schema(
            description = "user name",
            example = "alex",
            required = true)
    @NotBlank(message = "The field is required")
    @Size(max = 50, message = "Max length is 50")
    private final String username;

    @Schema(
            description = "user password",
            example = "dEf_5+",
            required = true)
    @NotBlank(message = "The field is required")
    @Size(min = 4, max = 50, message = "The password must be between 4 and 50 characters long")
    private final String password;

}
