package com.example.traveldiary.mapper.model;

import com.example.traveldiary.dto.response.ExpenseTypeRest;
import com.example.traveldiary.model.ExpenseType;
import org.springframework.core.convert.converter.Converter;

public class ToExpenseTypeRestConverter implements Converter<ExpenseType, ExpenseTypeRest> {
    @Override
    public ExpenseTypeRest convert(ExpenseType expenseType) {
        return ExpenseTypeRest.builder()
                .id(expenseType.getId())
                .name(expenseType.getName())
                .build();
    }
}
