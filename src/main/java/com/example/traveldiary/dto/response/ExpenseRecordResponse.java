package com.example.traveldiary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseRecordResponse {

    @Schema(description = "Unique identifier",
            example = "1")
    private final Long id;

    @Schema(example = "1")
    private final Integer recNo;

    @Schema(description = "the expense type")
    private final ExpenseTypeResponse expenseType;

    @Schema(description = "some text",
            example = "full tank refueling was enough")
    private final String comment;

    @Schema(description = "planned sum of expenses for this type",
            example = "2000")
    private final Integer planSum;

    @Schema(description = "total actual sum of expenses for this type",
            example = "0")
    private final Integer factSum;
}
