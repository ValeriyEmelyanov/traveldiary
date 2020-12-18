package com.example.traveldiary.dto.request;

import com.example.traveldiary.model.Rating;
import com.example.traveldiary.model.TravelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TravelDto {

    @Schema(
            description = "",
            example = "PLAN",
            required = true)
    private TravelStatus status;

    @Schema(
            description = "",
            example = "Anapa for weekend",
            required = true)
    private String title;

    @Schema(
            description = "date when to start the travel",
            example = "2021-03-08",
            required = true)
    private LocalDate startDate;

    @Schema(
            description = "date when to finish the travel",
            example = "2021-03-09",
            required = true)
    private LocalDate endDate;

    @Schema(
            description = "some description",
            example = "the Black sea, a deserted beach strewn with seashells",
            required = true)
    private String description;

    @Schema(
            description = "total planned sum of expenses",
            example = "2000",
            required = true)
    private Integer planTotalSum;

    @Schema(
            description = "total actual sum of expenses",
            example = "0",
            required = true)
    private Integer factTotalSum;

    @Schema(
            description = "",
            example = "NONE",
            required = true)
    private Rating rating;

    @Schema(
            description = "mark as favorites",
            example = "false",
            required = true)
    private Boolean favorite;

    @Schema(
            description = "list of expenses",
            required = false)
    private List<ExpenseRecordDto> expenses;

}
