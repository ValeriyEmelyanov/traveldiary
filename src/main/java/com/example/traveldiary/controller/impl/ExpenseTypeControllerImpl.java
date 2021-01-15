package com.example.traveldiary.controller.impl;

import com.example.traveldiary.controller.ExpenseTypeController;
import com.example.traveldiary.dto.request.ExpenseTypeRequest;
import com.example.traveldiary.dto.response.ExpenseTypeResponse;
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
    public ResponseEntity<List<ExpenseTypeResponse>> getList() {
        return ResponseEntity.ok(expenseTypeService.getList()
                .stream()
                .map(e -> conversionService.convert(e, ExpenseTypeResponse.class))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ExpenseTypeResponse> getById(Long id) {
        ExpenseType expenseType = expenseTypeService.getById(id);
        return ResponseEntity.ok(
                Objects.requireNonNull(conversionService.convert(expenseType, ExpenseTypeResponse.class)));
    }

    @Override
    public ResponseEntity<ExpenseTypeResponse> create(ExpenseTypeRequest expenseTypeRequest) {
        ExpenseType saved = expenseTypeService.save(
                Objects.requireNonNull(conversionService.convert(expenseTypeRequest, ExpenseType.class)));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(conversionService.convert(saved, ExpenseTypeResponse.class));
    }

    @Override
    public ResponseEntity<ExpenseTypeResponse> update(Long id,
                                                      ExpenseTypeRequest expenseTypeRequest) {
        ExpenseType updated = expenseTypeService.update(id,
                Objects.requireNonNull(conversionService.convert(expenseTypeRequest, ExpenseType.class)));
        return ResponseEntity.ok()
                .body(conversionService.convert(updated, ExpenseTypeResponse.class));
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        expenseTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
