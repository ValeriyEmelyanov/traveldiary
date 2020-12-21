package com.example.traveldiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDto {

    @Schema(
            description = "user name",
            example = "alex",
            required = true)
    private final String username;

    @Schema(
            description = "user password",
            example = "dEf_5+",
            required = true)
    private final String password;

}
