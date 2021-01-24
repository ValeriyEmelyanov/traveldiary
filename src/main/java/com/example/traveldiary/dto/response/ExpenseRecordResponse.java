package com.example.traveldiary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * An object for transferring data from a controller to a response about an expense record.
 */
@Data
@Builder
public class ExpenseRecordResponse {

    /**
     * An unique identifier.
     */
    @Schema(description = "an unique identifier",
            example = "1")
    private final Long id;

    /**
     * A record number.
     */
    @Schema(description = "a record number",
            example = "1")
    private final Integer recordNumber;

    /**
     * An expense type.
     */
    @Schema(description = "an expense type")
    private final ExpenseTypeResponse expenseType;

    /**
     * Some text.
     */
    @Schema(description = "some text",
            example = "full tank refueling was enough")
    private final String comment;

    /**
     * A planned sum of expenses for this type.
     */
    @Schema(description = "a planned sum of expenses for this type",
            example = "2000")
    private final Integer planSum;

    /**
     * An actual sum of expenses for this type.
     */
    @Schema(description = "an actual sum of expenses for this type",
            example = "0")
    private final Integer factSum;
}
