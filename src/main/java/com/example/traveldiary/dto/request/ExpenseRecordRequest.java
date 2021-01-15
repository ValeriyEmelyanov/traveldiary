package com.example.traveldiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * An object for trancferring data from a request to a controller about an expense record.
 */
@Data
@Builder
public class ExpenseRecordRequest {

    /**
     * A record number. The field is required. The value must be greater than one.
     */
    @Schema(example = "1",
            required = true)
    @NotNull(message = "The field is required")
    @Min(value = 1, message = "The value must be greater than one")
    private final Integer recordNumber;

    /**
     * An unique identifier of the expense type. The field is required.
     */
    @Schema(description = "an unique identifier of the expense type",
            example = "1",
            required = true)
    @NotNull(message = "The field is required")
    private final Long expenseTypeId;

    /**
     * Some text. Max length is 250.
     */
    @Schema(description = "some text",
            example = "full tank refueling was enough")
    @Size(max = 250, message = "Max length is 250")
    private final String comment;

    /**
     * A planned sum of expenses for this type. The field is required.
     */
    @Schema(description = "a planned sum of expenses for this type",
            example = "2000",
            required = true)
    @NotNull(message = "The field is required")
    @Min(value = 0, message = "The value must be greater than zero")
    private final Integer planSum;

    /**
     * A  total actual sum of expenses for this type. The field is required.
     * The value must be greater than zero.
     */
    @Schema(description = "a total actual sum of expenses for this type",
            example = "0",
            required = true)
    @NotNull(message = "The field is required")
    @Min(value = 0, message = "The value must be greater than zero")
    private final Integer factSum;
}
