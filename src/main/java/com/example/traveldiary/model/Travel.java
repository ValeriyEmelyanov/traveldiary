package com.example.traveldiary.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "travel")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class Travel extends AbstractEntity {

    @Schema(example = "PLAN",
            required = true)
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TravelStatus status;

    @Schema(example = "Anapa for weekend",
            required = true)
    @Column(name = "title")
    private String title;

    @Schema(description = "date when to start the travel",
            example = "2021-03-08",
            required = true)
    @Column(name = "start_date")
    private LocalDate startDate;

    @Schema(description = "date when to finish the travel",
            example = "2021-03-09",
            required = true)
    @Column(name = "end_date")
    private LocalDate endDate;

    @Schema(description = "some description",
            example = "the Black sea, a deserted beach strewn with seashells",
            required = true)
    @Column(name = "description")
    private String description;

    @Schema(description = "total planned sum of expenses",
            example = "5100",
            required = true)
    @Column(name = "plan_total_sum")
    private Integer planTotalSum;

    @Schema(description = "total actual sum of expenses",
            example = "0",
            required = true)
    @Column(name = "fact_total_sum")
    private Integer factTotalSum;

    @Schema(example = "NONE",
            required = true)
    @Column(name = "rating")
    @Enumerated
    private Rating rating;

    @Schema(description = "mark as favorites",
            example = "false",
            required = true)
    @Column(name = "favorite")
    private Boolean favorite;

    @Schema(description = "date of creation (filled in automatically)",
            example = "2020-11-27 19:41:43.623399")
    @Column(name = "created")
    @CreationTimestamp
    private LocalDateTime created;

    @Schema(description = "date of modification (filled in automatically)",
            example = "2020-11-26 20:20:42.473369")
    @Column(name = "modified")
    @UpdateTimestamp
    private LocalDateTime modified;

    @Schema(description = "owner of the travel",
            example = "alex",
            required = true)
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @Schema(description = "list of expenses")
    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ExpenseRecord> expenses;
}
