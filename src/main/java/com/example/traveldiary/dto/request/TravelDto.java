package com.example.traveldiary.dto.request;

import com.example.traveldiary.model.Rating;
import com.example.traveldiary.model.TravelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TravelDto {

    @Schema(example = "PLAN",
            required = true)
    private final TravelStatus status;

    @Schema(example = "Anapa for weekend",
            required = true)
    private final String title;

    @Schema(description = "date when to start the travel",
            example = "2021-03-08",
            required = true)
    private final LocalDate startDate;

    @Schema(description = "date when to finish the travel",
            example = "2021-03-09",
            required = true)
    private final LocalDate endDate;

    @Schema(description = "some description",
            example = "the Black sea, a deserted beach strewn with seashells",
            required = true)
    private final String description;

    @Schema(description = "total planned sum of expenses",
            example = "2000",
            required = true)
    private final Integer planTotalSum;

    @Schema(description = "total actual sum of expenses",
            example = "0",
            required = true)
    private final Integer factTotalSum;

    @Schema(example = "NONE",
            required = true)
    private final Rating rating;

    @Schema(description = "mark as favorites",
            example = "false",
            required = true)
    private final Boolean favorite;

    @Schema(description = "list of expenses")
    private final List<ExpenseRecordDto> expenses;
}
