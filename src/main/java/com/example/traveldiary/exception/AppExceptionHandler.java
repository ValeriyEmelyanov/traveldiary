package com.example.traveldiary.exception;

import com.example.traveldiary.dto.response.ErrorMessageRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = BadPasswordException.class)
    public ResponseEntity<Object> handleBadPasswordException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessageRest(LocalDateTime.now(), ex.getMessage()));
    }

    @ExceptionHandler(value = BadLoginPasswordException.class)
    public ResponseEntity<Object> handlerBadLoginPasswordException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageRest(LocalDateTime.now(), ex.getMessage()));
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessageRest(LocalDateTime.now(), ex.getMessage()));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessageRest(LocalDateTime.now(), ex.getMessage()));
    }

    @ExceptionHandler(value = ForbiddenException.class)
    public ResponseEntity<Object> handlerForbiddenException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessageRest(LocalDateTime.now(), ex.getMessage()));
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<Object> handlerUsernameNotFoundException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessageRest(LocalDateTime.now(), ex.getMessage()));
    }
}
