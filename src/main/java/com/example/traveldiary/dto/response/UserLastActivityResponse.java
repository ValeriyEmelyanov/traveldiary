package com.example.traveldiary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * An object for trancferring data from a controller to a response about a user last activity.
 */
@Data
@Builder
public class UserLastActivityResponse {

    /**
     * A date and time of activity.
     */
    @Schema(description = "a date and time of activity",
            example = "2020-12-14 19:09:51.873345",
            required = true)
    private final LocalDateTime lastActivity;

    /**
     * The method name with arguments (value or type name).
     */
    @Schema(description = "the method name with arguments (value or type name)",
            example = "create(TravelDto, user)",
            required = true)
    private final String description;
}
