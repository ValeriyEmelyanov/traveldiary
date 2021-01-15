package com.example.traveldiary.mapper.model;

import com.example.traveldiary.dto.response.ExpenseTypeResponse;
import com.example.traveldiary.model.ExpenseType;
import org.springframework.core.convert.converter.Converter;

public class ToExpenseTypeResponseConverter implements Converter<ExpenseType, ExpenseTypeResponse> {
    @Override
    public ExpenseTypeResponse convert(ExpenseType expenseType) {
        return ExpenseTypeResponse.builder()
                .id(expenseType.getId())
                .name(expenseType.getName())
                .build();
    }
}
