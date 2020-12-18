package com.example.traveldiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ExpenseRecordDto {

    @Schema(
            description = "",
            example = "1",
            required = true)
    private Integer recNo;

    @Schema(
            description = "Unique identifier of the expense type",
            example = "1",
            required = true)
    private Long expenseTypeId;

    @Schema(
            description = "some text",
            example = "full tank refueling was enough",
            required = false)
    private String comment;

    @Schema(
            description = "planned sum of expenses for this type",
            example = "2000",
            required = true)
    private Integer planSum;

    @Schema(
            description = "total actual sum of expenses for this type",
            example = "0",
            required = true)
    private Integer factSum;

}
