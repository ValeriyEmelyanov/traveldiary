package com.example.traveldiary.service.impl;

import com.example.traveldiary.exception.ErrorMessages;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.repository.ExpenseTypeRepository;
import com.example.traveldiary.service.ExpenseTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Slf4j
public class ExpenseTypeServiceImpl implements ExpenseTypeService {
    private final ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    public ExpenseTypeServiceImpl(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }

    @Transactional(readOnly = true)
    @Override
    @NonNull
    public List<ExpenseType> getList() {
        log.info("Requested Expense Type list");
        return expenseTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    @NonNull
    public ExpenseType getById(@NonNull Long id) {
        Assert.notNull(id, ErrorMessages.NULL_EXPENSE_TYPE_ID.getErrorMessage());

        log.info("Requested the Expense Type with id: {}", id);
        return expenseTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND.getErrorMessage()));
    }

    @Transactional
    @Override
    @NonNull
    public ExpenseType save(@NonNull ExpenseType expenseType) {
        Assert.notNull(expenseType, ErrorMessages.NULL_EXPENSE_TYPE_OBJECT.getErrorMessage());

        ExpenseType saved = save(null, expenseType, false);
        log.info("Saved a new Expense Type with id: {}", saved.getId());
        return saved;
    }

    @Transactional
    @Override
    @NonNull
    public ExpenseType update(@NonNull Long id, @NonNull ExpenseType expenseType) {
        Assert.notNull(id, ErrorMessages.NULL_EXPENSE_TYPE_ID.getErrorMessage());
        Assert.notNull(expenseType, ErrorMessages.NULL_EXPENSE_TYPE_OBJECT.getErrorMessage());

        ExpenseType updated = save(id, expenseType, true);
        log.info("Updated the Expense Type with id: {}", id);
        return updated;
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
    @NonNull
    public void delete(@NonNull Long id) {
        Assert.notNull(id, ErrorMessages.NULL_EXPENSE_TYPE_ID.getErrorMessage());

        getById(id);
        expenseTypeRepository.deleteById(id);
        log.info("Deleted the Expense Type with id: {}", id);
    }
}
