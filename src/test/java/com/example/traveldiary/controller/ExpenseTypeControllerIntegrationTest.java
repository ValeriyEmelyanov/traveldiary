package com.example.traveldiary.controller;

import com.example.traveldiary.dto.request.ExpenseTypeDto;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.repository.ExpenseTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseTypeControllerIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @BeforeEach
    void setUp() {
        if (expenseTypeRepository.findAll().size() == 0) {
            prepareTestData();
        }
    }

    private void prepareTestData() {
        ExpenseType type1 = ExpenseType.builder()
                .id(1L)
                .name("Transport")
                .build();
        ExpenseType type2 = ExpenseType.builder()
                .id(2L)
                .name("Food")
                .build();
        ExpenseType type3 = ExpenseType.builder()
                .id(3L)
                .name("Accommodation")
                .build();

        expenseTypeRepository.save(type1);
        expenseTypeRepository.save(type2);
        expenseTypeRepository.save(type3);
    }

    @Test
    @WithMockUser(authorities = {"expense_type:read"})
    void getList() throws Exception {
        int size = expenseTypeRepository.findAll().size();

        mockMvc.perform(get("/api/v1/expensetypes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(size)));
    }

    @Test
    void getListUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/expensetypes"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getListForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/expensetypes"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"expense_type:read"})
    void getById() throws Exception {
        mockMvc.perform(get("/api/v1/expensetypes/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Transport")));
    }

    @Test
    void getByIdUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/expensetypes/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getByIdForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/expensetypes/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"expense_type:write"})
    void create() throws Exception {
        int size = expenseTypeRepository.findAll().size();
        String typeName = "Souvenirs";
        ExpenseTypeDto dto = new ExpenseTypeDto(typeName);

        mockMvc.perform(post("/api/v1/expensetypes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated());

        List<ExpenseType> list = expenseTypeRepository.findAll();
        assertEquals(size + 1, list.size());
        assertEquals(1, list.stream().filter(e -> typeName.equals(e.getName())).count());
    }

    @Test
    void createUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/expensetypes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void createForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/expensetypes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"expense_type:write"})
    void update() throws Exception {
        long id = 2L;
        String typeName = "Food and delicious";
        ExpenseTypeDto dto = new ExpenseTypeDto(typeName);

        mockMvc.perform(put("/api/v1/expensetypes/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<ExpenseType> optional = expenseTypeRepository.findById(id);
        assertTrue(optional.isPresent());
        assertEquals(typeName, optional.get().getName());
    }

    @Test
    @WithMockUser(authorities = {"expense_type:write"})
    void updateNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/expensetypes/999")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUnauthorized() throws Exception {
        mockMvc.perform(put("/api/v1/expensetypes/2")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void updateForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/expensetypes/2")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"expense_type:write"})
    void deleteTest() throws Exception {
        int size = expenseTypeRepository.findAll().size();
        long id = 3L;

        mockMvc.perform(delete("/api/v1/expensetypes/" + id))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(size - 1, expenseTypeRepository.findAll().size());
        assertTrue(expenseTypeRepository.findById(id).isEmpty());
    }

    @Test
    @WithMockUser(authorities = {"expense_type:write"})
    void deleteNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/expensetypes/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/expensetypes/2"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void deleteForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/expensetypes/2"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}