package com.example.traveldiary.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExpenseRecordDto {
    private Integer recNo;
    private Long expenseTypeId;
    private String comment;
    private Integer planSum;
    private Integer factSum;
}
