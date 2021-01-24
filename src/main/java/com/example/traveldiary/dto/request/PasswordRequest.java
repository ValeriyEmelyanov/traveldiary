package com.example.traveldiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * An object for transferring data from a request to a controller about a password.
 */
@Data
@Builder
public class PasswordRequest {

    /**
     * An old password.
     */
    @Schema(description = "old password",
            example = "AbC1*3")
    private final String oldPassword;

    /**
     * A new password. The field is required. The password must be between 4 and 50 characters long.
     */
    @Schema(description = "new password",
            example = "dEf_5+",
            required = true)
    @NotBlank(message = "The field is required")
    @Size(min = 4, max = 50, message = "The password must be between 4 and 50 characters long")
    private final String password;

    /**
     * An matching password. The field is required.
     * The password must be between 8 and 50 characters long.
     */
    @Schema(description = "a matching password",
            example = "dEf_5+",
            required = true)
    @NotBlank(message = "The field is required")
    @Size(min = 4, max = 50, message = "The password must be between 8 and 50 characters long")
    private final String matchingPassword;
}
