package com.example.traveldiary.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String FIELD_ERROR_SEPARATOR = ": ";
    private static final String PATH_ERROR = "{}: {}";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + FIELD_ERROR_SEPARATOR + e.getDefaultMessage())
                .collect(Collectors.toList());
        return getExceptionResponseEntity(ex, HttpStatus.BAD_REQUEST, request, validationErrors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return getExceptionResponseEntity(ex, HttpStatus.BAD_REQUEST, request, List.of());
    }

    @ExceptionHandler(value = BadPasswordException.class)
    public ResponseEntity<Object> handleBadPasswordException(RuntimeException ex,
                                                             WebRequest request) {
        return getExceptionResponseEntity(ex, HttpStatus.BAD_REQUEST, request, List.of());
    }

    @ExceptionHandler(value = BadLoginPasswordException.class)
    public ResponseEntity<Object> handlerBadLoginPasswordException(RuntimeException ex,
                                                                   WebRequest request) {
        return getExceptionResponseEntity(ex, HttpStatus.BAD_REQUEST, request, List.of());
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex,
                                                            WebRequest request) {
        return getExceptionResponseEntity(ex, HttpStatus.BAD_REQUEST, request, List.of());
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(RuntimeException ex,
                                                           WebRequest request) {
        return getExceptionResponseEntity(ex, HttpStatus.NOT_FOUND, request, List.of());
    }

    @ExceptionHandler(value = ForbiddenException.class)
    public ResponseEntity<Object> handlerForbiddenException(RuntimeException ex,
                                                            WebRequest request) {
        return getExceptionResponseEntity(ex, HttpStatus.FORBIDDEN, request, List.of());
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<Object> handlerUsernameNotFoundException(RuntimeException ex,
                                                                   WebRequest request) {
        return getExceptionResponseEntity(ex, HttpStatus.NOT_FOUND, request, List.of());
    }

    private ResponseEntity<Object> getExceptionResponseEntity(final Exception ex,
                                                              final HttpStatus status,
                                                              final WebRequest request,
                                                              final List<String> errors) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("type", ex.getClass().getSimpleName());
        String path = request.getDescription(false);
        body.put("path", path);
        body.put("message", ex.getMessage());
        if (errors != null && !errors.isEmpty()) {
            body.put("errors", errors);
        }

        log.error(PATH_ERROR, path, ex.getMessage());
        return ResponseEntity.status(status).body(body);
    }
}
