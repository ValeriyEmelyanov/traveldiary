package com.example.traveldiary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * An object for trancferring data from a controller to a response about an expense type.
 */
@Data
@Builder
public class ExpenseTypeResponse {

    /**
     * An unique identifier.
     */
    @Schema(description = "an unique identifier",
            example = "1")
    private final Long id;

    /**
     * A name of the expense type.
     */
    @Schema(description = "a name of the expense type",
            example = "Transort")
    private final String name;
}
