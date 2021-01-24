package com.example.traveldiary.dto.response;

import com.example.traveldiary.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * An object for transferring data from a controller to a response about a user.
 */
@Data
@Builder
public class UserResponse {

    /**
     * An unique identifier.
     */
    @Schema(description = "an unique identifier",
            example = "1")
    private final Long id;

    /**
     * A user name.
     */
    @Schema(description = "a user name",
            example = "alex")
    private final String username;

    /**
     * A date of user creation (filled in automatically).
     */
    @Schema(description = "a date of user creation (filled in automatically)",
            example = "2020-11-27 19:41:43.623399")
    @CreationTimestamp
    private final LocalDateTime created;

    /**
     * A date of user modification (filled in automatically).
     */
    @Schema(description = "a date of user modification (filled in automatically)",
            example = "2020-11-26 20:20:42.473369")
    private final LocalDateTime modified;

    /**
     * Determines the user's ability to work in the service.
     */
    @Schema(description = "determines the user's ability to work in the service",
            example = "true")
    private final Boolean enabled;

    /**
     * User roles.
     */
    @Schema(description = "user roles",
            example = "[\"SENIOR\", \"USER\"]",
            required = true)
    @Enumerated(EnumType.STRING)
    private final Set<Role> roles;

    /**
     * When and what user did last time.
     */
    @Schema(description = "when and what user did last time")
    private final UserLastActivityResponse lastActivity;
}
