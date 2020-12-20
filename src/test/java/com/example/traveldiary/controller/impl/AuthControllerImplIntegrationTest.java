package com.example.traveldiary.controller.impl;

import com.example.traveldiary.Urls;
import com.example.traveldiary.dto.request.UserLoginDto;
import com.example.traveldiary.model.Role;
import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerImplIntegrationTest {

    private final String username = "user1";
    private final String password = "password";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        prepareTestData();
    }

    private void prepareTestData() {
        User user = User.builder()
                .id(1L)
                .username(username)
                .password(passwordEncoder.encode(password))
                .enabled(true)
                .roles(Set.of(Role.USER))
                .travels(List.of())
                .build();

        userRepository.save(user);
    }

    @Test
    void auth() throws Exception {
        UserLoginDto dto = UserLoginDto.builder()
                .username(username)
                .password(password)
                .build();

        mockMvc.perform(post(Urls.Auth.FULL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.token").hasJsonPath());
    }

    @Test
    void authUsernameFailure() throws Exception {
        UserLoginDto dto = UserLoginDto.builder()
                .username(username + "999")
                .password(password)
                .build();

        mockMvc.perform(post(Urls.Auth.FULL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void authPasswordFailure() throws Exception {
        UserLoginDto dto = UserLoginDto.builder()
                .username(username)
                .password(password + "wrong")
                .build();

        mockMvc.perform(post(Urls.Auth.FULL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}