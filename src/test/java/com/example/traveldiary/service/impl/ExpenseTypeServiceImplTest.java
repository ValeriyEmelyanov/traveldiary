package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.ExpenseTypeDto;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.repository.ExpenseTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

class ExpenseTypeServiceImplTest {

    @InjectMocks
    private ExpenseTypeServiceImpl expenseTypeService;

    @Mock
    private ExpenseTypeRepository expenseTypeRepository;

    private List<ExpenseType> testData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        prepareTestData();
    }

    private void prepareTestData() {
        testData = new ArrayList<>();
        testData.add(ExpenseType.builder()
                .id(1L)
                .name("Transport")
                .build());
        testData.add(ExpenseType.builder()
                .id(2L)
                .name("Food")
                .build());
        testData.add(ExpenseType.builder()
                .id(3L)
                .name("Accommodation")
                .build());
    }

    @Test
    void getList() {
        when(expenseTypeRepository.findAll())
                .thenReturn(testData);

        List<ExpenseType> receivedList = expenseTypeService.getList();

        assertNotNull(receivedList);
        assertEquals(testData, receivedList);
    }

    @Test
    void getById() {
        long id = 1L;
        int index = 0;

        when(expenseTypeRepository.findById(id))
                .thenReturn(Optional.of(testData.get(index)));

        ExpenseType receivedById = expenseTypeService.getById(id);

        assertNotNull(receivedById);
        assertEquals(testData.get(index), receivedById);
    }

    @Test
    void getByIdNotFound() {
        long notExistingId = 4L;

        when(expenseTypeRepository.findById(notExistingId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> expenseTypeService.getById(notExistingId));
    }

    @Test
    void save() {
        int testDataSizeBefore = testData.size();

        ExpenseTypeDto dto = new ExpenseTypeDto();
        dto.setName("Souvenirs");

        when(expenseTypeRepository.save(any()))
                .thenAnswer((Answer<Void>) invocation -> {
                    testData.add(invocation.getArgument(0));
                    return null;
                });

        expenseTypeService.save(dto);

        assertEquals(testDataSizeBefore + 1, testData.size());
        assertEquals(dto.getName(), testData.get(testData.size() - 1).getName());
    }

    @Test
    void update() {
        long id = 2L;
        int index = 1;

        ExpenseTypeDto dto = new ExpenseTypeDto();
        dto.setName("Nutrition");

        when(expenseTypeRepository.findById(id))
                .thenReturn(Optional.of(testData.get(index)));
        when(expenseTypeRepository.save(any()))
                .thenAnswer((Answer<Void>) invocation -> {
                    testData.get(index).setName(((ExpenseType) invocation.getArgument(0)).getName());
                    return null;
                });

        expenseTypeService.update(id, dto);

        assertEquals(dto.getName(), testData.get(index).getName());
    }

    @Test
    void delete() {
        long id = 1L;
        int index = 0;
        int testDataSizeBefore = testData.size();

        when(expenseTypeRepository.findById(id))
                .thenReturn(Optional.of(testData.get(index)));
        doAnswer((Answer<Void>) invocation -> {
            testData.remove(index);
            return null;
        }).when(expenseTypeRepository).deleteById(id);

        expenseTypeService.delete(id);

        assertEquals(testDataSizeBefore - 1, testData.size());
    }

    @Test
    void deleteNotFound() {
        long notExistingId = 4L;

        when(expenseTypeRepository.findById(notExistingId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> expenseTypeService.delete(notExistingId));
    }
}