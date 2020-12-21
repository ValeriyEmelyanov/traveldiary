package com.example.traveldiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseRecordDto {

    @Schema(example = "1",
            required = true)
    private final Integer recNo;

    @Schema(description = "Unique identifier of the expense type",
            example = "1",
            required = true)
    private final Long expenseTypeId;

    @Schema(description = "some text",
            example = "full tank refueling was enough")
    private final String comment;

    @Schema(description = "planned sum of expenses for this type",
            example = "2000",
            required = true)
    private final Integer planSum;

    @Schema(description = "total actual sum of expenses for this type",
            example = "0",
            required = true)
    private final Integer factSum;
}
