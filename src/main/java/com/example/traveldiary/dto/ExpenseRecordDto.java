package com.example.traveldiary.dto;

public class ExpenseRecordDto {
    private Long id;
//    private Long travelId;
    private Integer recNo;
    private Long expenseTypeId;
    private String comment;
    private Integer planSum;
    private Integer factPlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getTravelId() {
//        return travelId;
//    }
//
//    public void setTravelId(Long travelId) {
//        this.travelId = travelId;
//    }

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

    public Integer getFactPlan() {
        return factPlan;
    }

    public void setFactPlan(Integer factPlan) {
        this.factPlan = factPlan;
    }
}
