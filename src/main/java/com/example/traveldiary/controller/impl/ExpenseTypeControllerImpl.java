package com.example.traveldiary.controller.impl;

import com.example.traveldiary.controller.ExpenseTypeController;
import com.example.traveldiary.dto.request.ExpenseTypeDto;
import com.example.traveldiary.dto.response.ExpenseTypeRest;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ExpenseTypeControllerImpl implements ExpenseTypeController {
    private final ExpenseTypeService expenseTypeService;
    private final ConversionService conversionService;

    @Autowired
    public ExpenseTypeControllerImpl(ExpenseTypeService expenseTypeService,
                                     ConversionService conversionService) {
        this.expenseTypeService = expenseTypeService;
        this.conversionService = conversionService;
    }

    @Override
    public ResponseEntity<List<ExpenseTypeRest>> getList() {
        return ResponseEntity.ok(expenseTypeService.getList()
                .stream()
                .map(e -> conversionService.convert(e, ExpenseTypeRest.class))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ExpenseTypeRest> getById(Long id) {
        ExpenseType expenseType = expenseTypeService.getById(id);
        return ResponseEntity.ok(
                Objects.requireNonNull(conversionService.convert(expenseType, ExpenseTypeRest.class)));
    }

    @Override
    public ResponseEntity<ExpenseTypeRest> create(ExpenseTypeDto expenseTypeDto) {
        ExpenseType saved = expenseTypeService.save(
                Objects.requireNonNull(conversionService.convert(expenseTypeDto, ExpenseType.class)));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(conversionService.convert(saved, ExpenseTypeRest.class));
    }

    @Override
    public ResponseEntity<ExpenseTypeRest> update(Long id,
                                                  ExpenseTypeDto expenseTypeDto) {
        ExpenseType updated = expenseTypeService.update(id,
                Objects.requireNonNull(conversionService.convert(expenseTypeDto, ExpenseType.class)));
        return ResponseEntity.ok()
                .body(conversionService.convert(updated, ExpenseTypeRest.class));
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        expenseTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
