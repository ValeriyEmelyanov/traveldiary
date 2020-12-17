package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.ExpenseRecordDto;
import com.example.traveldiary.dto.TravelDto;
import com.example.traveldiary.exception.ForbiddenException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.mapper.TravelMapper;
import com.example.traveldiary.model.ExpenseRecord;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.TravelRepository;
import com.example.traveldiary.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

class TravelServiceImplTest {

    @InjectMocks
    private TravelServiceImpl travelService;

    @Mock
    private TravelRepository travelRepository;

    @Mock
    private UserService userService;

    @Mock
    private TravelMapper travelMapper;

    private List<Travel> testData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        prepareTestData();
    }

    private void prepareTestData() {
        testData = new ArrayList<>();
        testData.add(Travel.builder()
                .id(1L)
                .build());
        testData.add(Travel.builder()
                .id(2L)
                .build());
        testData.add(Travel.builder()
                .id(3L)
                .build());
    }

    @Test
    void getList() {
        when(travelRepository.findAll())
                .thenReturn(testData);

        List<Travel> receivedList = travelService.getList();

        assertNotNull(receivedList);
        assertEquals(testData, receivedList);
    }

    @Test
    void getById() {
        long id = 1L;
        int index = 0;

        when(travelRepository.findById(id))
                .thenReturn(Optional.of(testData.get(index)));

        Travel receivedById = travelService.getById(id);

        assertNotNull(receivedById);
        assertEquals(testData.get(index), receivedById);
    }

    @Test
    void getByIdNotFound() {
        long id = 4L;

        when(travelRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> travelService.getById(id));
    }

    @Test
    void save() {
        int testDataSizeBefore = testData.size();

        String username = "user";
        User user = getUser1L(username);

        TravelDto dto = getTravelDto();

        when(userService.getByUsername(username))
                .thenReturn(user);
        when(travelMapper.toTravel(dto))
                .thenReturn(getTravelFromDto(dto));
        when(travelRepository.save(any()))
                .thenAnswer((Answer<Void>) invocation -> {
                    testData.add(invocation.getArgument(0));
                    return null;
                });

        travelService.save(dto, username);

        assertEquals(testDataSizeBefore + 1, testData.size());
        Travel savedTravel = testData.get(testData.size() - 1);
        assertNotNull(savedTravel);
        assertEquals(user, savedTravel.getUser());
        assertNotNull(savedTravel.getExpenses());
        assertTrue(savedTravel.getExpenses().size() > 0);
        assertEquals(savedTravel, savedTravel.getExpenses().get(0).getTravel());
    }

    private Travel getTravelFromDto(TravelDto dto) {
        Travel travel = Travel.builder()
                .id(4L)
                .description(dto.getDescription())
                .build();

        List<ExpenseRecordDto> recordDtos = dto.getExpenses();
        List<ExpenseRecord> expenseRecords = new ArrayList<>();
        for (int i = 0; i < recordDtos.size(); i++) {
            expenseRecords.add(ExpenseRecord.builder()
                    .id((long) (i + 1))
                    .recNo(recordDtos.get(i).getRecNo())
                    .build());
        }
        travel.setExpenses(expenseRecords);

        return travel;
    }

    private TravelDto getTravelDto() {
        TravelDto dto = TravelDto.builder()
                .description("The best journey")
                .build();

        List<ExpenseRecordDto> expenseRecordDtos = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            expenseRecordDtos.add(ExpenseRecordDto.builder()
                    .recNo(i)
                    .expenseTypeId((long) i)
                    .build());
        }
        dto.setExpenses(expenseRecordDtos);

        return dto;
    }

    private User getUser1L(String username) {
        return User.builder()
                .id(1L)
                .username(username)
                .build();
    }

    @Test
    void update() {
        int index = 0;
        long id = 1L;

        String username = "user";
        User user = getUser1L(username);

        TravelDto dto = getTravelDto();

        Travel travel = getTravelFromDto(dto);
        travel.setUser(user);
        travel.setId(id);

        when(userService.getByUsername(username))
                .thenReturn(user);
        when(travelRepository.findById(id))
                .thenReturn(Optional.of(travel));
        doAnswer((Answer<Void>) invocation -> {
            testData.set(index, travel);
            return null;
        }).when(travelMapper).updateTravel(dto, travel);
        when(travelRepository.save(any()))
                .thenAnswer((Answer<Void>) invocation -> {
                    testData.set(index, invocation.getArgument(0));
                    return null;
                });

        travelService.update(id, dto, username);

        Travel updatedTravel = testData.get(index);
        assertNotNull(updatedTravel);
        assertEquals(user, updatedTravel.getUser());
        assertNotNull(updatedTravel.getExpenses());
        assertTrue(updatedTravel.getExpenses().size() > 0);
        assertEquals(updatedTravel, updatedTravel.getExpenses().get(0).getTravel());
    }

    @Test
    void updateForbidden() {
        long id = 1L;

        String username = "user";
        User user = getUser1L(username);

        TravelDto dto = getTravelDto();

        Travel travel = getTravelFromDto(dto);
        travel.setUser(user);
        travel.setId(id);

        when(userService.getByUsername(username))
                .thenReturn(User.builder().id(2L).build());
        when(travelRepository.findById(id))
                .thenReturn(Optional.of(travel));

        assertThrows(ForbiddenException.class, () -> travelService.update(id, dto, username));
    }

    @Test
    void delete() {
        long id = 1L;
        int index = 0;
        int testDataSizeBefore = testData.size();

        String username = "user";
        User user = getUser1L(username);

        testData.get(index).setUser(user);

        when(travelRepository.findById(id))
                .thenReturn(Optional.of(testData.get(index)));
        when(userService.getByUsername(username))
                .thenReturn(user);
        doAnswer((Answer<Void>) invocation -> {
            testData.remove(index);
            return null;
        }).when(travelRepository).deleteById(id);

        travelService.delete(id, username);

        assertEquals(testDataSizeBefore - 1, testData.size());
    }

    @Test
    void deleteNotFound() {
        long notExistingId = 1L;

        String username = "user";

        when(travelRepository.findById(notExistingId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> travelService.delete(notExistingId, username));
    }

    @Test
    void deleteForbidden() {
        int index = 0;
        long id = 1L;

        String username = "user";
        User user = getUser1L(username);

        testData.get(index).setUser(User.builder().id(2L).build());

        when(travelRepository.findById(id))
                .thenReturn(Optional.of(testData.get(index)));
        when(userService.getByUsername(username))
                .thenReturn(user);

        assertThrows(ForbiddenException.class, () -> travelService.delete(id, username));
    }
}