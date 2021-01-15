package com.example.traveldiary.exception;

public class BadLoginPasswordException extends RuntimeException {
    public BadLoginPasswordException(String message) {
        super(message);
    }
}
