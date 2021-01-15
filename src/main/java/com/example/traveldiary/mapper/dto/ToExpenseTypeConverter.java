package com.example.traveldiary.mapper.dto;

import com.example.traveldiary.dto.request.ExpenseTypeRequest;
import com.example.traveldiary.model.ExpenseType;
import org.springframework.core.convert.converter.Converter;

public class ToExpenseTypeConverter implements Converter<ExpenseTypeRequest, ExpenseType> {
    @Override
    public ExpenseType convert(ExpenseTypeRequest expenseTypeRequest) {
        return ExpenseType.builder()
                .name(expenseTypeRequest.getName())
                .build();
    }
}
