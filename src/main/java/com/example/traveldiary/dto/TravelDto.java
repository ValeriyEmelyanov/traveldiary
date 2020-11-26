package com.example.traveldiary.dto;

import com.example.traveldiary.model.Rating;
import com.example.traveldiary.model.TravelStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class TravelDto {
    private TravelStatus status;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Integer planTotalSum;
    private Integer factTotalSum;
    private Rating rating;
    private Boolean favorite;
    private List<ExpenseRecordDto> expenses;
}
