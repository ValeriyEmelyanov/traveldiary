package com.example.traveldiary.controller;

import com.example.traveldiary.dto.ExpenseTypeDto;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('expense_type:read')")
    public ResponseEntity<List<ExpenseType>> getList() {
        return ResponseEntity.ok(expenseTypeService.getList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('expense_type:read')")
    public ResponseEntity<ExpenseType> getById(@PathVariable Long id) {
        ExpenseType expenseType = expenseTypeService.getById(id);
        return ResponseEntity.ok(expenseType);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('expense_type:write')")
    public ResponseEntity<String> create(@RequestBody ExpenseTypeDto expenseTypeDto) {
        expenseTypeService.save(expenseTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('expense_type:write')")
    public ResponseEntity<String> update(@RequestBody ExpenseTypeDto expenseTypeDto) {
        expenseTypeService.update(expenseTypeDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('expense_type:write')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        expenseTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
