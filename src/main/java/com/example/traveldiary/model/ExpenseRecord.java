package com.example.traveldiary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class ExpenseRecord extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "travel_id")
    @JsonIgnore
    private Travel travel;

    @Column(name = "rec_no")
    private Integer recNo;

    @ManyToOne
    @JoinColumn(name = "expense_type_id")
    private ExpenseType expenseType;

    @Column(name = "comment")
    private String comment;

    @Column(name = "plan_sum")
    private Integer planSum;

    @Column(name = "fact_sum")
    private Integer factSum;

}
