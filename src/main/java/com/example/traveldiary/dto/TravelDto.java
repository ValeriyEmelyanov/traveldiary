package com.example.traveldiary.dto;

import com.example.traveldiary.model.Rating;
import com.example.traveldiary.model.Travel;

import java.time.LocalDate;
import java.util.List;

public class TravelDto {
    private Long id;
    private Travel.Status status;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Integer PlanTotalSum;
    private Integer FactTotalSum;
    private Rating rating;
    private Boolean isFavorite;
    private List<ExpenseRecordDto> expenses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Travel.Status getStatus() {
        return status;
    }

    public void setStatus(Travel.Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPlanTotalSum() {
        return PlanTotalSum;
    }

    public void setPlanTotalSum(Integer planTotalSum) {
        PlanTotalSum = planTotalSum;
    }

    public Integer getFactTotalSum() {
        return FactTotalSum;
    }

    public void setFactTotalSum(Integer factTotalSum) {
        FactTotalSum = factTotalSum;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public List<ExpenseRecordDto> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseRecordDto> expenses) {
        this.expenses = expenses;
    }
}
