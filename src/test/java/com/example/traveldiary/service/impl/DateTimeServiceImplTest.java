package com.example.traveldiary.service.impl;

import com.example.traveldiary.util.LocalDateTimeMatcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateTimeServiceImplTest {
    private static DateTimeServiceImpl dateTimeService;

    @BeforeAll
    static void setUp() {
        dateTimeService = new DateTimeServiceImpl();
    }

    @Test
    void getNow() {
        LocalDateTime before = LocalDateTime.now();
        String stringNow = dateTimeService.getNow();
        LocalDateTime after = LocalDateTime.now();

        assertNotNull(stringNow);
        assertTrue(stringNow.startsWith("Now is "));

        String strDateTime = stringNow.replace("Now is ", "");
        assertThat(strDateTime, LocalDateTimeMatcher.betweenInclusive(before, after));
    }

    @Test
    void getGreetingWithNull() {
        String greeting = dateTimeService.getGreeting(null);

        assertNotNull(greeting);
        assertThat(greeting, anyOf(
                equalTo("Good morning!"),
                equalTo("Good afternoon!"),
                equalTo("Good evening!")));
    }

    @Test
    void getGreetingWithName() {
        String name = "Alex";

        String greeting = dateTimeService.getGreeting(name);

        assertNotNull(greeting);
        assertThat(greeting, anyOf(
                startsWith("Good morning,"), startsWith("Good afternoon,"), startsWith("Good evening,")));
        assertThat(greeting, containsString(name));
        assertThat(greeting, endsWith("!"));
    }

}