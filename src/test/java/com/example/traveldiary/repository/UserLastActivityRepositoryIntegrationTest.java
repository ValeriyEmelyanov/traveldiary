package com.example.traveldiary.repository;

import com.example.traveldiary.model.Role;
import com.example.traveldiary.model.User;
import com.example.traveldiary.model.UserLastActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserLastActivityRepositoryIntegrationTest {

    @Autowired
    private UserLastActivityRepository lastActivityRepository;

    @Autowired
    private UserRepository userRepository;

    private final long user_id = 1L;
    private UserLastActivity lastActivity1;

    @BeforeEach
    void setUp() {
        if (lastActivityRepository.findAll().size() == 0) {
            prepareTestData();
        }
    }

    private void prepareTestData() {
        User user1 = User.builder()
                .id(user_id)
                .username("user11")
                .password("password11")
                .enabled(true)
                .roles(Set.of(Role.USER))
                .travels(List.of())
                .build();
        User user2 = User.builder()
                .id(2L)
                .username("user12")
                .password("password12")
                .enabled(true)
                .roles(Set.of(Role.USER))
                .travels(List.of())
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        lastActivity1 = UserLastActivity.builder()
                .user(user1)
                .lastActivity(LocalDateTime.of(2021, 1, 1, 0, 0))
                .description("description 1")
                .build();
        UserLastActivity lastActivity2 = UserLastActivity.builder()
                .user(user2)
                .lastActivity(LocalDateTime.of(2020, 1, 1, 0, 0))
                .description("description 2")
                .build();

        lastActivityRepository.save(lastActivity1);
        lastActivityRepository.save(lastActivity2);
    }

    @Test
    void findByUserId() {
        Optional<UserLastActivity> optional = lastActivityRepository.findByUserId(user_id);

        assertTrue(optional.isPresent());
        UserLastActivity byUserId = optional.get();
        assertEquals(lastActivity1.getId(), byUserId.getId());
        assertEquals(lastActivity1.getLastActivity(), byUserId.getLastActivity());
        assertEquals(lastActivity1.getDescription(), byUserId.getDescription());
    }
}