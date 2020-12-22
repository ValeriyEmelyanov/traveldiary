package com.example.traveldiary.controller.impl;

import com.example.traveldiary.Urls;
import com.example.traveldiary.dto.request.PasswordDto;
import com.example.traveldiary.dto.request.UserDto;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerImplIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String oldPassword = "password";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        prepareTestData();
    }

    private void prepareTestData() {
        User user1 = User.builder()
                .id(1L)
                .username("user1")
                .password(passwordEncoder.encode(oldPassword))
                .travels(List.of())
                .build();
        User user2 = User.builder()
                .id(2L)
                .username("user2")
                .travels(List.of())
                .build();
        User user3 = User.builder()
                .id(3L)
                .username("user3")
                .travels(List.of())
                .build();
        User admin = User.builder()
                .id(4L)
                .username("admin")
                .travels(List.of())
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(admin);
    }

    @Test
    @WithMockUser(authorities = {"user:read"})
    void getList() throws Exception {
        int size = userRepository.findAll().size();

        mockMvc.perform(get(Urls.Users.FULL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(size)));
    }

    @Test
    void getListUnauthorized() throws Exception {
        mockMvc.perform(get(Urls.Users.FULL))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getListForbidden() throws Exception {
        mockMvc.perform(get(Urls.Users.FULL))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"user:read"})
    void getById() throws Exception {
        int id = 1;

        mockMvc.perform(get(Urls.Users.FULL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.username", is("user1")));
    }

    @Test
    void getByIdUnauthorized() throws Exception {
        mockMvc.perform(get(Urls.Users.FULL + "/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getByIdForbidden() throws Exception {
        mockMvc.perform(get(Urls.Users.FULL + "/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"user:write"})
    void create() throws Exception {
        int size = userRepository.findAll().size();
        String username = "new user";
        String password = "secret";
        Set<Role> roles = Set.of(Role.SENIOR, Role.USER);
        UserDto dto = UserDto.builder()
                .username(username)
                .password(password)
                .enabled(true)
                .roles(roles)
                .build();
        LocalDateTime before = LocalDateTime.now();

        mockMvc.perform(post(Urls.Users.FULL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated());

        List<User> list = userRepository.findAll();
        assertEquals(size + 1, list.size());
        List<User> filtered = list.stream()
                .filter(e -> username.equals(e.getUsername()))
                .collect(Collectors.toList());
        assertEquals(1, filtered.size());
        User created = filtered.get(0);
        assertEquals(roles, created.getRoles());
        assertTrue(passwordEncoder.matches(password, created.getPassword()));
        assertTrue(before.isBefore(created.getCreated()));
        assertTrue(LocalDateTime.now().isAfter(created.getCreated()));
    }

    @Test
    void createUnauthorized() throws Exception {
        mockMvc.perform(post(Urls.Users.FULL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void createForbidden() throws Exception {
        UserDto dto = UserDto.builder()
                .username("username")
                .password("password")
                .enabled(true)
                .build();

        mockMvc.perform(post(Urls.Users.FULL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"user:write"})
    void update() throws Exception {
        long id = 2L;
        String username = "updated user";
        String password = "secret";
        Set<Role> roles = Set.of(Role.USER);
        UserDto dto = UserDto.builder()
                .username(username)
                .password(password)
                .enabled(true)
                .roles(roles)
                .build();

        mockMvc.perform(put(Urls.Users.FULL + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<User> optional = userRepository.findById(id);
        assertTrue(optional.isPresent());
        User updated = optional.get();
        assertEquals(username, updated.getUsername());
        assertEquals(roles, updated.getRoles());
        assertTrue(passwordEncoder.matches(password, updated.getPassword()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"user:write"})
    void changePassword() throws Exception {
        long id = 1L;
        String password = "secret";
        PasswordDto dto = PasswordDto.builder()
                .password(password)
                .matchingPassword(password)
                .build();

        mockMvc.perform(patch(Urls.Users.FULL + "/" + id + "/" + Urls.Users.Password.PART)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<User> optional = userRepository.findById(id);
        assertTrue(optional.isPresent());
        assertTrue(passwordEncoder.matches(password, optional.get().getPassword()));
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"user:profile"})
    void changePasswordProfile() throws Exception {
        long id = 1L;
        String password = "secret";
        PasswordDto dto = PasswordDto.builder()
                .oldPassword(oldPassword)
                .password(password)
                .matchingPassword(password)
                .build();

        mockMvc.perform(patch(Urls.Users.FULL + "/" + id + "/" + Urls.Users.Password.PART)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<User> optional = userRepository.findById(id);
        assertTrue(optional.isPresent());
        assertTrue(passwordEncoder.matches(password, optional.get().getPassword()));
    }

    @Test
    void changePasswordUnauthorized() throws Exception {
        mockMvc.perform(patch(Urls.Users.FULL + "/1/" + Urls.Users.Password.PART)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void changePasswordForbidden() throws Exception {
        PasswordDto dto = PasswordDto.builder()
                .password("password")
                .matchingPassword("password")
                .build();

        mockMvc.perform(patch(Urls.Users.FULL + "/1/" + Urls.Users.Password.PART)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"user:profile"})
    void changePasswordOtherUser() throws Exception {
        String password = "secret";
        PasswordDto dto = PasswordDto.builder()
                .password(password)
                .matchingPassword(password)
                .build();

        mockMvc.perform(patch(Urls.Users.FULL + "/2/" + Urls.Users.Password.PART)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"user:profile"})
    void changePasswordMismatch() throws Exception {
        String password = "secret";
        PasswordDto dto = PasswordDto.builder()
                .oldPassword(oldPassword)
                .password(password)
                .matchingPassword(password + "more")
                .build();

        mockMvc.perform(patch(Urls.Users.FULL + "/1/" + Urls.Users.Password.PART)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = {"user:write"})
    void deleteTest() throws Exception {
        int size = userRepository.findAll().size();
        long id = 3L;

        mockMvc.perform(delete(Urls.Users.FULL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(size - 1, userRepository.findAll().size());
        assertTrue(userRepository.findById(id).isEmpty());
    }

    @Test
    void deleteTestUnauthorized() throws Exception {
        mockMvc.perform(delete(Urls.Users.FULL + "/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void deleteTestForbidden() throws Exception {
        mockMvc.perform(delete(Urls.Users.FULL + "/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"user:write"})
    void deleteTestNotFound() throws Exception {
        mockMvc.perform(delete(Urls.Users.FULL + "/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}