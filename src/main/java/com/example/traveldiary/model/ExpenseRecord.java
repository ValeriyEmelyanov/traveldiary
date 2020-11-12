package com.example.traveldiary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ExpenseRecord extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "travel_id")
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
    private Integer factPlan;

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public Integer getRecNo() {
        return recNo;
    }

    public void setRecNo(Integer recNo) {
        this.recNo = recNo;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPlanSum() {
        return planSum;
    }

    public void setPlanSum(Integer planSum) {
        this.planSum = planSum;
    }

    public Integer getFactPlan() {
        return factPlan;
    }

    public void setFactPlan(Integer factPlan) {
        this.factPlan = factPlan;
    }
}
