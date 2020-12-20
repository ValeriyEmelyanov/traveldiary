package com.example.traveldiary.controller.impl;

import com.example.traveldiary.controller.PlayController;
import com.example.traveldiary.service.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayControllerImpl implements PlayController {
    private final DateTimeService dateTimeService;

    @Autowired
    public PlayControllerImpl(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    @Override
    public ResponseEntity<String> printDateTime() {
        return new ResponseEntity<>(
                dateTimeService.getNow(),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> printGreeting(String name) {
        return new ResponseEntity<>(
                dateTimeService.getGreeting(name),
                HttpStatus.OK);
    }
}
