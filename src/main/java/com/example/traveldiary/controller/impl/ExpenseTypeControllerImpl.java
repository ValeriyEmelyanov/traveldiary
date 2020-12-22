package com.example.traveldiary.controller.impl;

import com.example.traveldiary.controller.ExpenseTypeController;
import com.example.traveldiary.dto.request.ExpenseTypeDto;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpenseTypeControllerImpl implements ExpenseTypeController {
    private final ExpenseTypeService expenseTypeService;
    private final ConversionService conversionService;

    @Autowired
    public ExpenseTypeControllerImpl(ExpenseTypeService expenseTypeService, ConversionService conversionService) {
        this.expenseTypeService = expenseTypeService;
        this.conversionService = conversionService;
    }

    @Override
    public ResponseEntity<List<ExpenseType>> getList() {
        return ResponseEntity.ok(expenseTypeService.getList());
    }

    @Override
    public ResponseEntity<ExpenseType> getById(Long id) {
        ExpenseType expenseType = expenseTypeService.getById(id);
        return ResponseEntity.ok(expenseType);
    }

    @Override
    public ResponseEntity<String> create(ExpenseTypeDto expenseTypeDto) {
        expenseTypeService.save(conversionService.convert(expenseTypeDto, ExpenseType.class));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<String> update(Long id,
                                         ExpenseTypeDto expenseTypeDto) {
        expenseTypeService.update(id, conversionService.convert(expenseTypeDto, ExpenseType.class));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        expenseTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
