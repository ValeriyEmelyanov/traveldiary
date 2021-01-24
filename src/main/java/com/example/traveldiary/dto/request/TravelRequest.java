package com.example.traveldiary.dto.request;

import com.example.traveldiary.model.Rating;
import com.example.traveldiary.model.TravelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * An object for transferring data from a request to a controller about a travel.
 */
@Data
@Builder
public class TravelRequest {

    /**
     * A travel status. The field is required.
     */
    @Schema(example = "PLAN",
            required = true)
    @NotNull(message = "The field is required")
    private final TravelStatus status;

    /**
     * A title. The field is required. Max length is 150.
     */
    @Schema(example = "Anapa for weekend",
            required = true)
    @NotBlank(message = "The field is required")
    @Size(max = 150, message = "Max length is 150")
    private final String title;

    /**
     * A date when to start the travel. The field is required.
     */
    @Schema(description = "a date when to start the travel",
            example = "2021-03-08",
            required = true)
    @NotNull(message = "The field is required")
    private final LocalDate startDate;

    /**
     * A date when to finish the travel.
     */
    @Schema(description = "a date when to finish the travel",
            example = "2021-03-09",
            required = true)
    private final LocalDate endDate;

    /**
     * Some description. Max length is 1000.
     */
    @Schema(description = "some description",
            example = "the Black sea, a deserted beach strewn with seashells")
    @Size(max = 1000, message = "Max length is 1000")
    private final String description;

    /**
     * A total planned sum of expenses. The field is required. The value must be greater than zero.
     */
    @Schema(description = "a total planned sum of expenses",
            example = "2000",
            required = true)
    @NotNull(message = "The field is required")
    @Min(value = 0, message = "The value must be greater than zero")
    private final Integer planTotalSum;

    /**
     * A total actual sum of expenses. The field is required.
     * The value must be zero or greater than zero.
     */
    @Schema(description = "a total actual sum of expenses",
            example = "0",
            required = true)
    @NotNull(message = "The field is required")
    @Min(value = 0, message = "The value must be zero or greater than zero")
    private final Integer factTotalSum;

    /**
     * Rating. The field is required.
     */
    @Schema(example = "NONE",
            required = true)
    @NotNull(message = "The field is required")
    private final Rating rating;

    /**
     * A mark as favorites. The field is required.
     */
    @Schema(description = "a mark as favorites",
            example = "false",
            required = true)
    @NotNull(message = "The field is required")
    private final Boolean favorite;

    /**
     * List of expenses.
     */
    @Schema(description = "list of expenses")
    private final List<ExpenseRecordRequest> expenses;
}
