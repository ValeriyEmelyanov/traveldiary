package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.ExpenseTypeDto;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.repository.ExpenseTypeRepository;
import com.example.traveldiary.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    public void setExpenseTypeRepository(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }

    @Override
    public List<ExpenseType> getList() {
        return expenseTypeRepository.findAll();
    }

    @Override
    public ExpenseType getById(Long id) {
        return expenseTypeRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ExpenseTypeDto expenseTypeDto) {
        ExpenseType expenseType = new ExpenseType();
        expenseType.setId(expenseTypeDto.getId());
        expenseType.setName(expenseTypeDto.getName());

        expenseTypeRepository.save(expenseType);
    }

    @Override
    public void delete(Long id) {
        expenseTypeRepository.deleteById(id);
    }
}
