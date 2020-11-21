package com.example.traveldiary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = BadPasswordException.class)
    public ResponseEntity<String> handleBadPasswordException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(RuntimeException ex) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(value = UnexpectedException.class)
    public ResponseEntity<String> handleUnexpectedException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
