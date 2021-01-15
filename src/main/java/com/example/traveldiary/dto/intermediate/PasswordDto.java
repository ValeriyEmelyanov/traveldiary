package com.example.traveldiary.dto.intermediate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * An object for trancferring a password data between apllication layers.
 */
@AllArgsConstructor
@Getter
@Builder
public class PasswordDto {

    /**
     * An old passworld.
     */
    private final String oldPassword;

    /**
     * A new password.
     */
    private final String password;

    /**
     * A matching password.
     */
    private final String matchingPassword;
}
