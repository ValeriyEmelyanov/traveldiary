package com.example.traveldiary.service;

import com.example.traveldiary.dto.ExpenseTypeDto;
import com.example.traveldiary.model.ExpenseType;

import java.util.List;

public interface ExpenseTypeService {
    List<ExpenseType> getList();

    ExpenseType getById(Long id);

    boolean notExists(Long id);

    void save(ExpenseTypeDto expenseTypeDto);

    void delete(Long id);
}
