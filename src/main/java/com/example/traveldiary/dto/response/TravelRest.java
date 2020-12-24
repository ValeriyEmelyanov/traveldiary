package com.example.traveldiary.dto.response;

import com.example.traveldiary.model.Rating;
import com.example.traveldiary.model.TravelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TravelRest {

    @Schema(description = "Unique identifier",
            example = "1")
    private final Long id;

    @Schema(example = "PLAN")
    @Enumerated(EnumType.STRING)
    private TravelStatus status;

    @Schema(example = "Anapa for weekend")
    private String title;

    @Schema(description = "date when to start the travel",
            example = "2021-03-08")
    private LocalDate startDate;

    @Schema(description = "date when to finish the travel",
            example = "2021-03-09")
    private LocalDate endDate;

    @Schema(description = "some description",
            example = "the Black sea, a deserted beach strewn with seashells")
    private String description;

    @Schema(description = "total planned sum of expenses",
            example = "5100")
    private Integer planTotalSum;

    @Schema(description = "total actual sum of expenses",
            example = "0")
    private Integer factTotalSum;

    @Schema(example = "NONE",
            required = true)
    @Enumerated
    private Rating rating;

    @Schema(description = "mark as favorites",
            example = "false")
    private Boolean favorite;

    @Schema(description = "date of creation (filled in automatically)",
            example = "2020-11-27 19:41:43.623399")
    private LocalDateTime created;

    @Schema(description = "date of modification (filled in automatically)",
            example = "2020-11-26 20:20:42.473369")
    private LocalDateTime modified;

    @Schema(description = "owner of the travel",
            example = "alex")
    private String user;

    @Schema(description = "list of expenses")
    private List<ExpenseRecordRest> expenses;
}
