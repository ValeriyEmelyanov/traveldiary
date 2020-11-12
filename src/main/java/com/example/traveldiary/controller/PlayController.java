package com.example.traveldiary.controller;

import com.example.traveldiary.service.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayController {
    private DateTimeService dateTimeService;

    @Autowired
    public void setDateTimeService(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    @GetMapping("/now")
    public ResponseEntity<String> printDateTime() {
        return new ResponseEntity<>(
                dateTimeService.getNow(),
                HttpStatus.OK);
    }

    @GetMapping({"/greeting", "/greeting/{name}"})
    public ResponseEntity<String> printGreeting(@PathVariable(required = false) String name) {
        return new ResponseEntity<>(
                dateTimeService.getGreeting(name),
                HttpStatus.OK);
    }

}
