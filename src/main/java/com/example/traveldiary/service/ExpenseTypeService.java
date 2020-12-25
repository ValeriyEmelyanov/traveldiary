package com.example.traveldiary.service;

import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.ExpenseType;

import java.util.List;

/**
 * Service for working with {@link ExpenseType expenseType} entity
 */
public interface ExpenseTypeService {

    /**
     * Method for getting full list of {@link ExpenseType expenseType}s.
     *
     * @return full list of {@link ExpenseType expenseType}
     */
    List<ExpenseType> getList();

    /**
     * Method for finding a {@link ExpenseType expenseType} by its unique identifier.
     * The input argument should not be null,
     * otherwise will be thrown IllegalArgumentException.
     *
     * @param id {@link ExpenseType expenseType} unique identifier
     * @return {@link ExpenseType expenseType} object with unique identifier like in the argument
     * @throws IllegalArgumentException if input id is null
     * @throws NotFoundException        if there is no {@link ExpenseType expenseType} object
     *                                  with unique identifier like in the argument
     */
    ExpenseType getById(Long id);

    /**
     * Method for saving a {@link ExpenseType expenseType} in a repository.
     * The input argument should not be null,
     * otherwise will be thrown IllegalArgumentException.
     *
     * @param expenseType {@link ExpenseType expenseType} object to save
     * @return saved {@link ExpenseType expenseType} object
     * @throws IllegalArgumentException if input {@link ExpenseType expenseType} object is null
     */
    ExpenseType save(ExpenseType expenseType);

    /**
     * Method for updating a {@link ExpenseType expenseType}.
     * Input arguments should not be null,
     * otherwise will be thrown IllegalArgumentException.
     *
     * @param id          {@link ExpenseType expenseType} unique identifier
     * @param expenseType {@link ExpenseType expenseType} object to update
     * @return updated {@link ExpenseType expenseType} object
     * @throws IllegalArgumentException if input id or {@link ExpenseType expenseType} object is null
     * @throws NotFoundException        if there is no {@link ExpenseType expenseType} object
     *                                  with unique identifier like in the argument
     */
    ExpenseType update(Long id, ExpenseType expenseType);

    /**
     * Method for deleting a {@link ExpenseType expenseType}.
     * The input argument should not be null,
     * otherwise will be thrown IllegalArgumentException.
     *
     * @param id {@link ExpenseType expenseType} unique identifier
     * @throws IllegalArgumentException if input id is null
     * @throws NotFoundException        if there is no {@link ExpenseType expenseType} object
     *                                  with unique identifier like in the argument
     */
    void delete(Long id);
}
