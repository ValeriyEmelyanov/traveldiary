package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.request.ExpenseTypeDto;
import com.example.traveldiary.exception.BadRequestException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.repository.ExpenseTypeRepository;
import com.example.traveldiary.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {
    private final ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    public ExpenseTypeServiceImpl(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExpenseType> getList() {
        return expenseTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public ExpenseType getById(Long id) {
        if (id == null) {
            throw new BadRequestException();
        }
        return expenseTypeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    @Override
    public void save(ExpenseTypeDto expenseTypeDto) {
        save(null, expenseTypeDto, false);
    }

    @Transactional
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

    @Transactional
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new BadRequestException();
        }

        getById(id);

        expenseTypeRepository.deleteById(id);
    }
}
