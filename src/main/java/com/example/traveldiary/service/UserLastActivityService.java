package com.example.traveldiary.service;

import java.time.LocalDateTime;

/**
 * Service for working with {@link com.example.traveldiary.model.User user} last activity
 */
public interface UserLastActivityService {

    /**
     * Method for saving a {@link com.example.traveldiary.model.UserLastActivity userLastActivity} in a repository.
     * The input argument should not be null,
     * otherwise will be thrown IllegalArgumentException.
     *
     * @param username    name of {@link com.example.traveldiary.model.User user} which comleted activity
     * @param dateTime    date and time of activity
     * @param description description of activity
     * @throws IllegalArgumentException if any of input arguments is null
     */
    void save(String username, LocalDateTime dateTime, String description);
}
