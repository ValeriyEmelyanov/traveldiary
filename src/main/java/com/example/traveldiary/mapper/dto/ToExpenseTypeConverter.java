package com.example.traveldiary.mapper.dto;

import com.example.traveldiary.dto.request.ExpenseTypeDto;
import com.example.traveldiary.model.ExpenseType;
import org.springframework.core.convert.converter.Converter;

public class ToExpenseTypeConverter implements Converter<ExpenseTypeDto, ExpenseType> {
    @Override
    public ExpenseType convert(ExpenseTypeDto expenseTypeDto) {
        return ExpenseType.builder()
                .name(expenseTypeDto.getName())
                .build();
    }
}
