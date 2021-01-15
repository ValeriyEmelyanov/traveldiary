package com.example.traveldiary.service;

/**
 * Service for working with date and time
 */
public interface DateTimeService {

    /**
     * The method for getting current date and time.
     *
     * @return string with current date and tine
     */
    String getNow();

    /**
     * Method for getting a greeting corresponding to the time of day
     *
     * @param name - name for greeting
     * @return string with greeting
     */
    String getGreeting(String name);
}
