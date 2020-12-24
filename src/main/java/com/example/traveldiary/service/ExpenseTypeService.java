package com.example.traveldiary.service;

import com.example.traveldiary.model.ExpenseType;

import java.util.List;

public interface ExpenseTypeService {
    List<ExpenseType> getList();

    ExpenseType getById(Long id);

    ExpenseType save(ExpenseType expenseType);

    ExpenseType update(Long id, ExpenseType expenseType);

    void delete(Long id);
}
