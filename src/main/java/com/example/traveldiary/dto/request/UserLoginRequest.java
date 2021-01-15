package com.example.traveldiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * An object for trancferring data from a request to a controller about an user login.
 */
@Data
@Builder
public class UserLoginRequest {

    /**
     * A user name. The field is required. Max length is 50.
     */
    @Schema(
            description = "a user name",
            example = "alex",
            required = true)
    @NotBlank(message = "The field is required")
    @Size(max = 50, message = "Max length is 50")
    private final String username;

    /**
     * A user password. The field is required.
     * The password must be between 4 and 50 characters long.
     */
    @Schema(
            description = "an user password",
            example = "dEf_5+",
            required = true)
    @NotBlank(message = "The field is required")
    @Size(min = 4, max = 50, message = "The password must be between 4 and 50 characters long")
    private final String password;

}
