package com.example.traveldiary.controller.impl;

import com.example.traveldiary.Urls;
import com.example.traveldiary.dto.request.ExpenseRecordRequest;
import com.example.traveldiary.dto.request.TravelRequest;
import com.example.traveldiary.model.ExpenseRecord;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.model.Rating;
import com.example.traveldiary.model.Role;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.model.TravelStatus;
import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.ExpenseTypeRepository;
import com.example.traveldiary.repository.TravelRepository;
import com.example.traveldiary.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TravelControllerImplIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        if (travelRepository.findAll().isEmpty()) {
            prepareTestData();
        }
    }

    private void prepareTestData() {
        ExpenseType type1 = ExpenseType.builder()
                .id(1L)
                .name("Expense type #1")
                .build();
        ExpenseType type2 = ExpenseType.builder()
                .id(2L)
                .name("Expense type #2")
                .build();

        expenseTypeRepository.save(type1);
        expenseTypeRepository.save(type2);

        User user1 = User.builder()
                .id(1L)
                .username("user1")
                .enabled(true)
                .roles(Set.of(Role.USER))
                .travels(List.of())
                .build();
        User user2 = User.builder()
                .id(2L)
                .username("user2")
                .enabled(true)
                .roles(Set.of(Role.USER))
                .travels(List.of())
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        Travel travel1 = Travel.builder()
                .id(1L)
                .status(TravelStatus.PLAN)
                .title("Travel #1")
                .startDate(LocalDate.of(2020, 12, 31))
                .endDate(LocalDate.of(2021, 1, 1))
                .description("Description #1")
                .planTotalSum(5000)
                .factTotalSum(0)
                .rating(Rating.NONE)
                .favorite(false)
                .user(user1)
                .build();
        ExpenseRecord record1t1 = ExpenseRecord.builder()
                .travel(travel1)
                .recNo(1)
                .expenseType(type1)
                .comment("Comment #1 of travel #1")
                .planSum(3000)
                .factSum(0)
                .build();
        ExpenseRecord record2t1 = ExpenseRecord.builder()
                .travel(travel1)
                .recNo(2)
                .expenseType(type2)
                .comment("Comment #2 of travel #1")
                .planSum(2000)
                .factSum(0)
                .build();
        travel1.setExpenses(List.of(record1t1, record2t1));

        Travel travel2 = Travel.builder()
                .id(2L)
                .status(TravelStatus.PLAN)
                .title("Travel #2")
                .startDate(LocalDate.of(2021, 1, 7))
                .endDate(LocalDate.of(2021, 1, 7))
                .description("Description #2")
                .planTotalSum(7000)
                .factTotalSum(0)
                .rating(Rating.NONE)
                .favorite(false)
                .user(user1)
                .build();
        ExpenseRecord record1t2 = ExpenseRecord.builder()
                .travel(travel2)
                .recNo(1)
                .expenseType(type1)
                .comment("Comment #1 of travel #2")
                .planSum(7000)
                .factSum(0)
                .build();
        travel2.setExpenses(List.of(record1t2));

        Travel travel3 = Travel.builder()
                .id(3L)
                .status(TravelStatus.DONE)
                .title("Travel #3")
                .startDate(LocalDate.of(2020, 7, 10))
                .endDate(LocalDate.of(2021, 7, 17))
                .description("Description #3")
                .planTotalSum(15000)
                .factTotalSum(20000)
                .rating(Rating.GOOD)
                .favorite(false)
                .user(user1)
                .build();
        ExpenseRecord record1t3 = ExpenseRecord.builder()
                .travel(travel3)
                .recNo(1)
                .expenseType(type1)
                .comment("Comment #1 of travel #3")
                .planSum(15000)
                .factSum(20000)
                .build();
        travel3.setExpenses(List.of(record1t3));

        travelRepository.save(travel1);
        travelRepository.save(travel2);
        travelRepository.save(travel3);
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"travel:read"})
    void getList() throws Exception {
        int size = travelRepository.findAll().size();

        mockMvc.perform(get(Urls.Travels.FULL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(size)));
    }

    @Test
    void getListUnauthorized() throws Exception {
        mockMvc.perform(get(Urls.Travels.FULL))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getListForbidden() throws Exception {
        mockMvc.perform(get(Urls.Travels.FULL))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"travel:read"})
    void getById() throws Exception {
        mockMvc.perform(get(Urls.Travels.FULL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Travel #1")));
    }

    @Test
    void getByIdUnauthorized() throws Exception {
        mockMvc.perform(get(Urls.Travels.FULL + "/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getByIdForbidden() throws Exception {
        mockMvc.perform(get(Urls.Travels.FULL + "/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"travel:write"})
    void create() throws Exception {
        int size = travelRepository.findAll().size();
        TravelRequest dto = getTravelDtoForCreate();

        mockMvc.perform(post(Urls.Travels.FULL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated());

        List<Travel> list = travelRepository.findAll();
        assertEquals(size + 1, list.size());
        List<Travel> filtered = list.stream()
                .filter(e -> dto.getTitle().equals(e.getTitle()))
                .collect(Collectors.toList());
        assertTrue(filtered.size() > 0);
        Travel saved = filtered.get(0);
        assertEquals(dto.getStatus(), saved.getStatus());
        assertEquals(dto.getTitle(), saved.getTitle());
        assertEquals(dto.getStartDate(), saved.getStartDate());
        assertEquals(dto.getEndDate(), saved.getEndDate());
        assertEquals(dto.getDescription(), saved.getDescription());
        assertEquals(dto.getPlanTotalSum(), saved.getPlanTotalSum());
        assertEquals(dto.getFactTotalSum(), saved.getFactTotalSum());
        assertEquals(dto.getRating(), saved.getRating());
        assertEquals(dto.getFavorite(), saved.getFavorite());
        assertEquals("user1", saved.getUser().getUsername());
        List<ExpenseRecordRequest> recordDtos = dto.getExpenses();
        List<ExpenseRecord> records = saved.getExpenses();
        assertEquals(recordDtos.size(), records.size());
        for (int i = 0; i < recordDtos.size(); i++) {
            ExpenseRecordRequest recordDto = recordDtos.get(i);
            ExpenseRecord record = records.get(i);
            assertEquals(recordDto.getRecNo(), record.getRecNo());
            assertEquals(
                    recordDto.getExpenseTypeId(),
                    record.getExpenseType().getId());
            assertEquals(recordDto.getComment(), record.getComment());
            assertEquals(recordDto.getPlanSum(), record.getPlanSum());
            assertEquals(recordDto.getFactSum(), record.getFactSum());
        }
    }

    private TravelRequest getTravelDtoForCreate() {
        ExpenseRecordRequest recordDto1 = ExpenseRecordRequest.builder()
                .recNo(1)
                .expenseTypeId(1L)
                .comment("Comment #1 of saved travel")
                .planSum(1000)
                .factSum(0)
                .build();
        ExpenseRecordRequest recordDto2 = ExpenseRecordRequest.builder()
                .recNo(2)
                .expenseTypeId(2L)
                .comment("Comment #2 of saved travel")
                .planSum(2000)
                .factSum(0)
                .build();

        return TravelRequest.builder()
                .status(TravelStatus.DONE)
                .title("Travel #4")
                .startDate(LocalDate.of(2021, 2, 23))
                .endDate(LocalDate.of(2021, 2, 23))
                .description("Description #4")
                .planTotalSum(3000)
                .factTotalSum(0)
                .rating(Rating.NONE)
                .favorite(true)
                .expenses(List.of(recordDto1, recordDto2))
                .build();
    }

    @Test
    void createUnauthorized() throws Exception {
        mockMvc.perform(post(Urls.Travels.FULL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void createForbidden() throws Exception {
        mockMvc.perform(post(Urls.Travels.FULL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(getTravelDtoForCreate())))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"travel:write"})
    void update() throws Exception {
        long id = 2L;
        TravelRequest dto = getTravelDtoForUpdate();

        mockMvc.perform(put(Urls.Travels.FULL + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Travel> optional = travelRepository.findById(id);
        assertTrue(optional.isPresent());
        Travel updated = optional.get();
        assertEquals(dto.getStatus(), updated.getStatus());
        assertEquals(dto.getTitle(), updated.getTitle());
        assertEquals(dto.getStartDate(), updated.getStartDate());
        assertEquals(dto.getEndDate(), updated.getEndDate());
        assertEquals(dto.getDescription(), updated.getDescription());
        assertEquals(dto.getPlanTotalSum(), updated.getPlanTotalSum());
        assertEquals(dto.getFactTotalSum(), updated.getFactTotalSum());
        assertEquals(dto.getRating(), updated.getRating());
        assertEquals(dto.getFavorite(), updated.getFavorite());
        assertEquals("user1", updated.getUser().getUsername());
        List<ExpenseRecordRequest> recordDtos = dto.getExpenses();
        List<ExpenseRecord> records = updated.getExpenses();
        assertEquals(recordDtos.size(), records.size());
        ExpenseRecordRequest recordDto = recordDtos.get(0);
        ExpenseRecord record = records.get(0);
        assertEquals(recordDto.getRecNo(), record.getRecNo());
        assertEquals(
                recordDto.getExpenseTypeId(),
                record.getExpenseType().getId());
        assertEquals(recordDto.getComment(), record.getComment());
        assertEquals(recordDto.getPlanSum(), record.getPlanSum());
        assertEquals(recordDto.getFactSum(), record.getFactSum());
    }

    private TravelRequest getTravelDtoForUpdate() {
        ExpenseRecordRequest recordDto1 = ExpenseRecordRequest.builder()
                .recNo(1)
                .expenseTypeId(1L)
                .comment("Comment #1 of travel #2 - fact")
                .planSum(7000)
                .factSum(7010)
                .build();

        return TravelRequest.builder()
                .status(TravelStatus.DONE)
                .title("Travel #2")
                .startDate(LocalDate.of(2021, 1, 7))
                .endDate(LocalDate.of(2021, 1, 7))
                .description("Description #2 - done!")
                .planTotalSum(7000)
                .factTotalSum(7010)
                .rating(Rating.EXCELLENT)
                .favorite(false)
                .expenses(List.of(recordDto1))
                .build();
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"travel:write"})
    void updateNotFound() throws Exception {
        mockMvc.perform(put(Urls.Travels.FULL + "/999")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(getTravelDtoForUpdate())))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUnauthorized() throws Exception {
        mockMvc.perform(put(Urls.Travels.FULL + "/2")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void updateForbidden() throws Exception {
        mockMvc.perform(put(Urls.Travels.FULL + "/2")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(getTravelDtoForUpdate())))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"travel:write"})
    void updateOtherUser() throws Exception {
        mockMvc.perform(put(Urls.Travels.FULL + "/2")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(getTravelDtoForUpdate())))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"travel:write"})
    void deleteTest() throws Exception {
        int size = travelRepository.findAll().size();
        long id = 3L;

        mockMvc.perform(delete(Urls.Travels.FULL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(size - 1, travelRepository.findAll().size());
        assertTrue(travelRepository.findById(id).isEmpty());
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"travel:write"})
    void deleteNotFound() throws Exception {
        mockMvc.perform(delete(Urls.Travels.FULL + "/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUnauthorized() throws Exception {
        mockMvc.perform(delete(Urls.Travels.FULL + "/2"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void deleteForbidden() throws Exception {
        mockMvc.perform(delete(Urls.Travels.FULL + "/2"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"travel:write"})
    void deleteOtherUser() throws Exception {
        mockMvc.perform(delete(Urls.Travels.FULL + "/2"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}