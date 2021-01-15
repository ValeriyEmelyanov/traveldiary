package com.example.traveldiary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "expense_record")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class ExpenseRecord extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "travel_id")
    @JsonIgnore
    private Travel travel;

    @Schema(example = "1",
            required = true)
    @Column(name = "rec_no")
    private Integer recordNumber;

    @Schema(description = "the expense type",
            required = true)
    @ManyToOne
    @JoinColumn(name = "expense_type_id")
    private ExpenseType expenseType;

    @Schema(description = "some text",
            example = "full tank refueling was enough")
    @Column(name = "comment")
    private String comment;

    @Schema(description = "planned sum of expenses for this type",
            example = "2000",
            required = true)
    @Column(name = "plan_sum")
    private Integer planSum;

    @Schema(description = "total actual sum of expenses for this type",
            example = "0",
            required = true)
    @Column(name = "fact_sum")
    private Integer factSum;
}
