package com.example.traveldiary.dto.request;

import com.example.traveldiary.model.Rating;
import com.example.traveldiary.model.TravelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TravelDto {

    @Schema(example = "PLAN",
            required = true)
    @NotNull(message = "The field is required")
    private final TravelStatus status;

    @Schema(example = "Anapa for weekend",
            required = true)
    @NotBlank(message = "The field is required")
    @Size(max = 150, message = "Max length is 150")
    private final String title;

    @Schema(description = "date when to start the travel",
            example = "2021-03-08",
            required = true)
    @NotNull(message = "The field is required")
    private final LocalDate startDate;

    @Schema(description = "date when to finish the travel",
            example = "2021-03-09",
            required = true)
    private final LocalDate endDate;

    @Schema(description = "some description",
            example = "the Black sea, a deserted beach strewn with seashells",
            required = true)
    @Size(max = 1000, message = "Max length is 1000")
    private final String description;

    @Schema(description = "total planned sum of expenses",
            example = "2000",
            required = true)
    @NotNull(message = "The field is required")
    @Min(value = 0, message = "The value must be greater than zero")
    private final Integer planTotalSum;

    @Schema(description = "total actual sum of expenses",
            example = "0",
            required = true)
    @NotNull(message = "The field is required")
    @Min(value = 0, message = "The value must be greater than zero")
    private final Integer factTotalSum;

    @Schema(example = "NONE",
            required = true)
    @NotNull(message = "The field is required")
    private final Rating rating;

    @Schema(description = "mark as favorites",
            example = "false",
            required = true)
    @NotNull(message = "The field is required")
    private final Boolean favorite;

    @Schema(description = "list of expenses")
    private final List<ExpenseRecordDto> expenses;
}
