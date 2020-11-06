package com.example.traveldiary.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class PlayController {

    @GetMapping("/now")
    public ResponseEntity<String> printDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return new ResponseEntity<>(
                String.format("Now is %s", formatter.format(LocalDateTime.now())),
                HttpStatus.OK);
    }

}
