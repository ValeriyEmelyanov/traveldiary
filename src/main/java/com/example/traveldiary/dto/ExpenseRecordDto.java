package com.example.traveldiary.dto;

public class ExpenseRecordDto {
    private Long id;
    private Integer recNo;
    private Long expenseTypeId;
    private String comment;
    private Integer planSum;
    private Integer factSum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRecNo() {
        return recNo;
    }

    public void setRecNo(Integer recNo) {
        this.recNo = recNo;
    }

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
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

    public Integer getFactSum() {
        return factSum;
    }

    public void setFactSum(Integer factSum) {
        this.factSum = factSum;
    }
}
