package com.example.traveldiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class ExpenseRecordRequest {

    @Schema(example = "1",
            required = true)
    @NotNull(message = "The field is required")
    @Min(value = 1, message = "The value must be greater than one")
    private final Integer recNo;

    @Schema(description = "Unique identifier of the expense type",
            example = "1",
            required = true)
    @NotNull(message = "The field is required")
    private final Long expenseTypeId;

    @Schema(description = "some text",
            example = "full tank refueling was enough")
    @Size(max = 250, message = "Max length is 250")
    private final String comment;

    @Schema(description = "planned sum of expenses for this type",
            example = "2000",
            required = true)
    @NotNull(message = "The field is required")
    @Min(value = 0, message = "The value must be greater than zero")
    private final Integer planSum;

    @Schema(description = "total actual sum of expenses for this type",
            example = "0",
            required = true)
    @NotNull(message = "The field is required")
    @Min(value = 0, message = "The value must be greater than zero")
    private final Integer factSum;
}
