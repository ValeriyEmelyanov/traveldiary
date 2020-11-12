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

    public enum Status {
        PLAN("Plan"), DONE("Done"), CANCELED("Canceled");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "title")
    private String title;

    @Column(name = "travel_date")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @Column(name = "plan_total_sum")
    private Integer PlanTotalSum;

    @Column(name = "fact_total_sum")
    private Integer FactTotalSum;

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ExpenseRecord> expenses;

    @Column(name = "rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(name = "favorite")
    private Boolean favorite;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public List<ExpenseRecord> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseRecord> expenses) {
        this.expenses = expenses;
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
}
