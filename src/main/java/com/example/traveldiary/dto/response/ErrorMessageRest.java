package com.example.traveldiary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorMessageRest {
    private final LocalDateTime timestamp;
    private final String message;
}
