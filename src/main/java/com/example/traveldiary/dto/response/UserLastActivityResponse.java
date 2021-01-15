package com.example.traveldiary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserLastActivityResponse {

    @Schema(description = "date and time of activity",
            example = "2020-12-14 19:09:51.873345",
            required = true)
    private final LocalDateTime lastActivity;

    @Schema(description = "the method name with arguments (value or type name)",
            example = "create(TravelDto, user)",
            required = true)
    private final String description;
}
