package com.example.traveldiary.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "travel")
public class Travel extends AbstractEntity {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TravelStatus status;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "plan_total_sum")
    private Integer planTotalSum;

    @Column(name = "fact_total_sum")
    private Integer factTotalSum;

    @Column(name = "rating")
    @Enumerated
    private Rating rating;

    @Column(name = "favorite")
    private Boolean favorite;

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ExpenseRecord> expenses;

    public Travel() {
    }

    public Travel(Long id) {
        super(id);
    }

    public TravelStatus getStatus() {
        return status;
    }

    public void setStatus(TravelStatus status) {
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

    public void setStartDate(LocalDate date) {
        this.startDate = date;
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
        return planTotalSum;
    }

    public void setPlanTotalSum(Integer planTotalSum) {
        this.planTotalSum = planTotalSum;
    }

    public Integer getFactTotalSum() {
        return factTotalSum;
    }

    public void setFactTotalSum(Integer factTotalSum) {
        this.factTotalSum = factTotalSum;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public List<ExpenseRecord> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseRecord> expenses) {
        this.expenses = expenses;
    }
}
