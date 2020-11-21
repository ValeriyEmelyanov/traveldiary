package com.example.traveldiary.exception;

public class BadPasswordException extends RuntimeException {

    public BadPasswordException(String message) {
        super(message);
    }

}
