package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.ExpenseTypeDto;
import com.example.traveldiary.exception.BadRequestException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.repository.ExpenseTypeRepository;
import com.example.traveldiary.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {
    private final ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    public ExpenseTypeServiceImpl(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }

    @Override
    public List<ExpenseType> getList() {
        return expenseTypeRepository.findAll();
    }

    @Override
    public ExpenseType getById(Long id) {
        if (id == null) {
            throw new BadRequestException();
        }
        return expenseTypeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void save(ExpenseTypeDto expenseTypeDto) {
        save(null, expenseTypeDto, false);
    }

    @Override
    public void update(Long id, ExpenseTypeDto expenseTypeDto) {
        if (id == null) {
            throw new BadRequestException();
        }
        save(id, expenseTypeDto, true);
    }

    private void save(Long id, ExpenseTypeDto expenseTypeDto, boolean isUpdate) {
        if (expenseTypeDto == null) {
            throw new BadRequestException();
        }

        ExpenseType expenseType;
        if (isUpdate) {
            expenseType = getById(id);
        } else {
            expenseType = new ExpenseType();
        }
        expenseType.setName(expenseTypeDto.getName());

        expenseTypeRepository.save(expenseType);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new BadRequestException();
        }

        getById(id);

        expenseTypeRepository.deleteById(id);
    }
}
