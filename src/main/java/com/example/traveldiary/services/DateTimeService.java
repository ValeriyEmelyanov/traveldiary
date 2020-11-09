package com.example.traveldiary.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateTimeService {

    public String getNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("Now is %s", formatter.format(LocalDateTime.now()));
    }

    public String getGreeting(String name) {
        return String.format("%s%s!", getGreeting(), name == null ? "" : ", " + name);
    }

    private String getGreeting() {
        LocalTime time = LocalTime.now();

        final LocalTime midnight = LocalTime.of(0, 0);
        final LocalTime noon = LocalTime.of(12, 0);
        if (time.isAfter(midnight) && time.isBefore(noon)) {
            return "Good morning";
        }

        LocalTime sixPm = LocalTime.of(18, 0);
        if (time.equals(noon) || (time.isAfter(noon) && time.isBefore(sixPm))) {
            return "Good afternoon";
        }

        return "Good evening";
    }
}
