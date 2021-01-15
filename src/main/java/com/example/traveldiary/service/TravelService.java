package com.example.traveldiary.service;

import com.example.traveldiary.exception.ForbiddenException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.model.User;

import java.util.List;

/**
 * Service for working with {@link Travel travel} entity
 */
public interface TravelService {

    /**
     * Method for getting full list of {@link Travel travel}s.
     *
     * @return full list of {@link Travel travel}s
     */
    List<Travel> getList();

    /**
     * Method for finding a {@link Travel travel} by its unique identifier.
     * The input argument should not be null,
     * otherwise will be thrown IllegalArgumentException.
     *
     * @param id {@link Travel travel} unique identifier
     * @return {@link Travel travel} object with unique identifier like in the argument
     * @throws IllegalArgumentException if input id is null
     * @throws NotFoundException        if there is no {@link Travel travel} object
     *                                  with unique identifier like in the argument
     */
    Travel getById(Long id);

    /**
     * Method for saving a {@link Travel travel} in a repository.
     * Input arguments should not be null,
     * otherwise will be thrown IllegalArgumentException.
     *
     * @param travel   {@link Travel travel} object to save
     * @param username name of {@link User user} which is owner of the travel
     * @return saved {@link Travel travel} object
     * @throws IllegalArgumentException if input {@link Travel travel} object is null
     */
    Travel save(Travel travel, String username);

    /**
     * Method for updating a {@link Travel travel}.
     * Input arguments should not be null,
     * otherwise will be thrown IllegalArgumentException.
     *
     * @param id       {@link Travel travel} unique identifier
     * @param travel   {@link Travel travel} object to update
     * @param username name of {@link User user} which is owner of the travel
     * @return updated {@link Travel travel} object
     * @throws IllegalArgumentException if any of input arguments is null
     * @throws NotFoundException        if there is no {@link Travel travel} object
     *                                  with unique identifier like in the argument
     *                                  or user with input username is not found
     * @throws ForbiddenException       if user is not owner of the travel
     */
    Travel update(Long id, Travel travel, String username);

    /**
     * Method for deleting a {@link Travel travel}.
     * The input argument should not be null,
     * otherwise will be thrown IllegalArgumentException.
     *
     * @param id       {@link Travel travel} unique identifier
     * @param username name of {@link User user} which is owner of the travel
     * @throws IllegalArgumentException if input id or username is null
     * @throws NotFoundException        if there is no {@Link Travel travel} object
     *                                  with unique identifier like in the argument
     *                                  or user with input username is not found
     * @throws ForbiddenException       if user is not owner of the travel
     */
    void delete(Long id, String username);
}
