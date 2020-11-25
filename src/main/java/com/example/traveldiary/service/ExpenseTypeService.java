package com.example.traveldiary.service;

import com.example.traveldiary.dto.ExpenseTypeDto;
import com.example.traveldiary.model.ExpenseType;

import java.util.List;

public interface ExpenseTypeService {
    List<ExpenseType> getList();

    ExpenseType getById(Long id);

    void save(ExpenseTypeDto expenseTypeDto);

    void update(Long id, ExpenseTypeDto expenseTypeDto);

    void delete(Long id);
}
