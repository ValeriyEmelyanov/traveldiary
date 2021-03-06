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

/**
 * An object for transferring data from a controller to a response about a travel.
 */
@Data
@Builder
public class TravelResponse {

    /**
     * An unique identifier.
     */
    @Schema(description = "an unique identifier",
            example = "1")
    private final Long id;

    /**
     * A status.
     */
    @Schema(example = "PLAN")
    @Enumerated(EnumType.STRING)
    private TravelStatus status;

    /**
     * A title.
     */
    @Schema(example = "Anapa for weekend")
    private String title;

    /**
     * A date when to start the travel.
     */
    @Schema(description = "a date when to start the travel",
            example = "2021-03-08")
    private LocalDate startDate;

    /**
     * A date when to finish the travel.
     */
    @Schema(description = "a date when to finish the travel",
            example = "2021-03-09")
    private LocalDate endDate;

    /**
     * Some description.
     */
    @Schema(description = "some description",
            example = "the Black sea, a deserted beach strewn with seashells")
    private String description;

    /**
     * A total planned sum of expenses.
     */
    @Schema(description = "a total planned sum of expenses",
            example = "5100")
    private Integer planTotalSum;

    /**
     * A total actual sum of expenses.
     */
    @Schema(description = "a total actual sum of expenses",
            example = "0")
    private Integer factTotalSum;

    /**
     * A rating.
     */
    @Schema(example = "NONE",
            required = true)
    @Enumerated
    private Rating rating;

    /**
     * A mark as favorites.
     */
    @Schema(description = "a mark as favorites",
            example = "false")
    private Boolean favorite;

    /**
     * A date of creation (filled in automatically).
     */
    @Schema(description = "a date of creation (filled in automatically)",
            example = "2020-11-27 19:41:43.623399")
    private LocalDateTime created;

    /**
     * A date of modification (filled in automatically).
     */
    @Schema(description = "a date of modification (filled in automatically)",
            example = "2020-11-26 20:20:42.473369")
    private LocalDateTime modified;

    /**
     * An owner of the travel.
     */
    @Schema(description = "an owner of the travel",
            example = "alex")
    private String user;

    /**
     * list of expenses.
     */
    @Schema(description = "list of expenses")
    private List<ExpenseRecordResponse> expenses;
}
