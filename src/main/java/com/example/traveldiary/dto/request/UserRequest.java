package com.example.traveldiary.dto.request;

import com.example.traveldiary.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * An object for transferring data from a request to a controller about a user.
 */
@Data
@Builder
public class UserRequest {

    /**
     * A user name. The field is required. Max length is 50.
     */
    @Schema(description = "a user name",
            example = "alex",
            required = true)
    @NotBlank(message = "The field is required")
    @Size(max = 50, message = "Max length is 50")
    private final String username;

    /**
     * A user password. The field is required. The password must be between 4 and 50 characters long.
     */
    @Schema(description = "a user password",
            example = "dEf_5+",
            required = true)
    @NotBlank(message = "The field is required")
    @Size(min = 4, max = 50, message = "The password must be between 4 and 50 characters long")
    private final String password;

    /**
     * Determines the user's ability to work in the service. The field is required.
     */
    @Schema(description = "determines the user's ability to work in the service",
            example = "true",
            required = true)
    @NotNull(message = "The field is required")
    private final Boolean enabled;

    /**
     * User roles.
     */
    @Schema(description = "user roles",
            example = "[\"SENIOR\", \"USER\"]")
    private final Set<Role> roles;
}
