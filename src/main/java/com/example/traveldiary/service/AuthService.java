package com.example.traveldiary.service;

import com.example.traveldiary.exception.BadLoginPasswordException;
import com.example.traveldiary.model.User;

/**
 * Service for working with authentication
 */
public interface AuthService {

    /**
     * A method for getting a token by username and password.
     * Input arguments should not be null,
     * otherwise will be thrown IllegalArgumentException.
     *
     * @param username {@link User user}`s name (login)
     * @param password {@link User user}`s password
     * @return string containing a security token
     * @throws IllegalArgumentException  if input username or password is null
     * @throws BadLoginPasswordException if username or password is invalid
     */
    String auth(String username, String password);
}
