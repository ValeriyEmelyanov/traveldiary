package com.example.traveldiary.service.impl;

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
        LocalDateTime before = LocalDateTime.now().withNano(0).withSecond(0);
        String strintNow = dateTimeService.getNow();
        LocalDateTime after = LocalDateTime.now().withNano(0).withSecond(0);

        assertNotNull(strintNow);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.parse(strintNow.replace("Now is ", ""), formatter);

        assertTrue(before.isBefore(now) || before.equals(now));
        assertTrue(after.isAfter(now) || after.equals(now));
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