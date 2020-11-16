package com.example.traveldiary.controller;

import com.example.traveldiary.dto.ExpenseTypeDto;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expensetype")
public class ExpenseTypeController {
    private final ExpenseTypeService expenseTypeService;

    @Autowired
    public ExpenseTypeController(ExpenseTypeService expenseTypeService) {
        this.expenseTypeService = expenseTypeService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ExpenseType>> getList() {
        return ResponseEntity.ok(expenseTypeService.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseType> getById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        ExpenseType expenseType = expenseTypeService.getById(id);
        if (expenseType == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(expenseType);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ExpenseTypeDto expenseTypeDto) {
        if (expenseTypeDto == null) {
            return ResponseEntity.badRequest().build();
        }

        expenseTypeService.save(expenseTypeDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody ExpenseTypeDto expenseTypeDto) {
        if (expenseTypeDto == null || expenseTypeDto.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (expenseTypeService.notExists(expenseTypeDto.getId())) {
            return ResponseEntity.notFound().build();
        }

        expenseTypeService.save(expenseTypeDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        if (expenseTypeService.notExists(id)) {
            return ResponseEntity.notFound().build();
        }

        expenseTypeService.delete(id);

        return ResponseEntity.ok().build();
    }
}
