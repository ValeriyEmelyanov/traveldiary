package com.example.traveldiary.service.impl;

import com.example.traveldiary.exception.ErrorMessages;
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
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }
        return expenseTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND.getErrorMessage()));
    }

    @Transactional
    @Override
    public void save(ExpenseType expenseType) {
        save(null, expenseType, false);
    }

    @Transactional
    @Override
    public void update(Long id, ExpenseType expenseType) {
        if (id == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }
        save(id, expenseType, true);
    }

    private void save(Long id, ExpenseType expenseType, boolean isUpdate) {
        if (expenseType == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }

        if (isUpdate) {
            ExpenseType expenseTypeSaved = getById(id);
            expenseType.setId(expenseTypeSaved.getId());
        }

        expenseTypeRepository.save(expenseType);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }

        getById(id);

        expenseTypeRepository.deleteById(id);
    }
}
