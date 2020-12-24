package com.example.traveldiary.service.impl;

import com.example.traveldiary.exception.ErrorMessages;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.repository.ExpenseTypeRepository;
import com.example.traveldiary.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
        Assert.notNull(id, ErrorMessages.NULL_EXPENSE_TYPE_ID.getErrorMessage());

        return expenseTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND.getErrorMessage()));
    }

    @Transactional
    @Override
    public ExpenseType save(ExpenseType expenseType) {
        Assert.notNull(expenseType, ErrorMessages.NULL_EXPENSE_TYPE_OBJECT.getErrorMessage());

        return save(null, expenseType, false);
    }

    @Transactional
    @Override
    public ExpenseType update(Long id, ExpenseType expenseType) {
        Assert.notNull(id, ErrorMessages.NULL_EXPENSE_TYPE_ID.getErrorMessage());
        Assert.notNull(expenseType, ErrorMessages.NULL_EXPENSE_TYPE_OBJECT.getErrorMessage());

        return save(id, expenseType, true);
    }

    private ExpenseType save(Long id, ExpenseType expenseType, boolean isUpdate) {
        if (isUpdate) {
            ExpenseType expenseTypeSaved = getById(id);
            expenseType.setId(expenseTypeSaved.getId());
        }

        return expenseTypeRepository.save(expenseType);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Assert.notNull(id, ErrorMessages.NULL_EXPENSE_TYPE_ID.getErrorMessage());

        getById(id);
        expenseTypeRepository.deleteById(id);
    }
}
