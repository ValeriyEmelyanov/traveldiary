package com.example.traveldiary.repository;

import com.example.traveldiary.model.Role;
import com.example.traveldiary.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private User user;
    private final String username = "user";

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username(username)
                .enabled(true)
                .roles(Set.of(Role.USER))
                .build();
        User user2 = User.builder()
                .username("user2")
                .build();
        User user3 = User.builder()
                .username("user3")
                .build();

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    @Test
    void findByUsername() {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        assertTrue(optionalUser.isPresent());
        User byUsername = optionalUser.get();
        assertEquals(user, byUsername);
        assertEquals(username, byUsername.getUsername());
        assertEquals(user.getEnabled(), byUsername.getEnabled());
        assertEquals(user.getRoles(), byUsername.getRoles());
    }
}