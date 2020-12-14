package com.example.traveldiary.service.impl;

import com.example.traveldiary.model.User;
import com.example.traveldiary.model.UserLastActivity;
import com.example.traveldiary.repository.UserLastActivityRepository;
import com.example.traveldiary.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserLastActivityServiceImplTest {

    @InjectMocks
    private UserLastActivityServiceImpl lastActivityService;

    @Mock
    private UserLastActivityRepository lastActivityRepository;

    @Mock
    private UserService userService;

    private final String username = "user";
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = User.builder()
                .id(1L)
                .username(username)
                .build();
    }

    @Test
    void save() {
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        String description = "new description";
        UserLastActivity lastActivity = UserLastActivity.builder()
                .user(user)
                .lastActivity(dateTime)
                .description(description)
                .build();
        List<UserLastActivity> testList = new ArrayList<>();

        when(userService.getByUsername(username))
                .thenReturn(user);
        when(lastActivityRepository.findByUserId(user.getId()))
                .thenReturn(Optional.empty());
        when(lastActivityRepository.save(any()))
                .thenAnswer((Answer<Void>) invocation -> {
                    testList.add(lastActivity);
                    return null;
                });

        lastActivityService.save(username, dateTime, description);

        assertEquals(1, testList.size());
    }

    @Test
    void saveUpdate() {
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        String description = "updated description";

        UserLastActivity lastActivity = UserLastActivity.builder()
                .id(1L)
                .user(user)
                .lastActivity(LocalDateTime.of(2020, 1, 1, 0, 0))
                .description("old description")
                .build();

        when(userService.getByUsername(username))
                .thenReturn(user);
        when(lastActivityRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(lastActivity));

        lastActivityService.save(username, dateTime, description);

        assertEquals(dateTime, lastActivity.getLastActivity());
        assertEquals(description, lastActivity.getDescription());
    }
}