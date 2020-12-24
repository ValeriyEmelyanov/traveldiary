package com.example.traveldiary.service.impl;

import com.example.traveldiary.service.DateTimeService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateTimeServiceImpl implements DateTimeService {
    private static final LocalTime SIX_PM = LocalTime.of(18, 0);

    @Override
    @NonNull
    public String getNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("Now is %s", formatter.format(LocalDateTime.now()));
    }

    @Override
    @NonNull
    public String getGreeting(@Nullable String name) {
        return String.format("%s%s!", defineGreeting(), name == null ? "" : ", " + name);
    }

    private String defineGreeting() {
        LocalTime time = LocalTime.now();

        if (time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(LocalTime.NOON)) {
            return "Good morning";
        }

        if (time.equals(LocalTime.NOON) || (time.isAfter(LocalTime.NOON) && time.isBefore(SIX_PM))) {
            return "Good afternoon";
        }

        return "Good evening";
    }
}
