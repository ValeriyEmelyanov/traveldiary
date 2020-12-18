package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.request.PasswordDto;
import com.example.traveldiary.dto.request.UserDto;
import com.example.traveldiary.exception.BadPasswordException;
import com.example.traveldiary.exception.ForbiddenException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.mapper.UserMapper;
import com.example.traveldiary.model.Role;
import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepositiry;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    private List<User> testData;
    private PasswordEncoder realPasswordEncoder;
    private final String oldPassword = "oldPassword";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        realPasswordEncoder = new BCryptPasswordEncoder(12);
        prepareTestData();
    }

    private void prepareTestData() {
        testData = new ArrayList<>();
        testData.add(User.builder()
                .id(1L)
                .password(realPasswordEncoder.encode(oldPassword))
                .build());
        testData.add(User.builder()
                .id(2L)
                .build());
        testData.add(User.builder()
                .id(3L)
                .build());
    }

    @Test
    void getList() {
        when(userRepositiry.findAll())
                .thenReturn(testData);

        List<User> receivedList = userService.getList();

        assertNotNull(receivedList);
        assertEquals(testData, receivedList);
    }

    @Test
    void getById() {
        long id = 1L;
        int index = 0;

        when(userRepositiry.findById(id))
                .thenReturn(Optional.of(testData.get(index)));

        User receivedById = userService.getById(id);

        assertNotNull(receivedById);
        assertEquals(testData.get(index), receivedById);
    }

    @Test
    void getByIdNotFound() {
        long id = 4L;

        when(userRepositiry.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getById(id));
    }

    @Test
    void getByUsername() {
        String userName = "user";
        int index = 0;

        when(userRepositiry.findByUsername(userName))
                .thenReturn(Optional.of(testData.get(index)));

        User receivedByUsername = userService.getByUsername(userName);

        assertNotNull(receivedByUsername);
        assertEquals(testData.get(index), receivedByUsername);
    }

    @Test
    void getByUsernameNotFound() {
        String userName = "userNotExists";

        when(userRepositiry.findByUsername(userName))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getByUsername(userName));
    }

    @Test
    void save() {
        int testDataSizeBefore = testData.size();
        String password = "secret";

        UserDto dto = UserDto.builder()
                .username("newUser")
                .password(password)
                .build();

        when(userMapper.toUser(dto))
                .thenReturn(User.builder()
                        .id(4L)
                        .username(dto.getUsername())
                        .build());
        when(passwordEncoder.encode(password))
                .thenReturn(realPasswordEncoder.encode(password));
        when(userRepositiry.save(any()))
                .thenAnswer((Answer<Void>) invocation -> {
                    testData.add(invocation.getArgument(0));
                    return null;
                });

        userService.save(dto);

        assertEquals(testDataSizeBefore + 1, testData.size());
        assertTrue(realPasswordEncoder.matches(password, testData.get(testData.size() - 1).getPassword()));
    }

    @Test
    void update() {
        long id = 2L;
        int index = 1;
        String password = "secret";

        UserDto dto = UserDto.builder()
                .username("updatedUser")
                .password(password)
                .build();

        when(userRepositiry.findById(id))
                .thenReturn(Optional.of(testData.get(index)));
        doAnswer((Answer<Void>) invocation -> {
            testData.get(index).setUsername(((UserDto) invocation.getArgument(0)).getUsername());
            return null;
        }).when(userMapper).updateUser(dto, testData.get(index));
        when(passwordEncoder.encode(password))
                .thenReturn(realPasswordEncoder.encode(password));
        when(userRepositiry.save(any()))
                .thenAnswer((Answer<Void>) invocation -> {
                    testData.set(index, invocation.getArgument(0));
                    return null;
                });

        userService.update(id, dto);

        assertTrue(realPasswordEncoder.matches(password, testData.get(index).getPassword()));
    }

    @Test
    void changePassword() {
        long id = 1L;
        int index = 0;
        String userName = "user";
        String password = "secret";

        PasswordDto dto = PasswordDto.builder()
                .oldPassword(oldPassword)
                .password(password)
                .matchingPassword(password)
                .build();

        when(userRepositiry.findByUsername(userName))
                .thenReturn(Optional.of(testData.get(index)));
        when(userRepositiry.findById(id))
                .thenReturn(Optional.of(testData.get(index)));
        when(passwordEncoder.encode(password))
                .thenReturn(realPasswordEncoder.encode(password));
        when(passwordEncoder.matches(oldPassword, testData.get(index).getPassword()))
                .thenReturn(realPasswordEncoder.matches(oldPassword, testData.get(index).getPassword()));
        when(userRepositiry.save(any()))
                .thenAnswer((Answer<Void>) invocation -> {
                    testData.set(index, invocation.getArgument(0));
                    return null;
                });

        userService.changePassword(userName, null, id, dto);

        assertTrue(realPasswordEncoder.matches(password, testData.get(index).getPassword()));
    }

    @Test
    void changePasswordAdmin() {
        long id = 1L;
        int index = 0;

        int adminIndex = 1;
        String adminName = "admin";

        String password = "secret";

        PasswordDto dto = PasswordDto.builder()
                .oldPassword(oldPassword)
                .password(password)
                .matchingPassword(password)
                .build();

        when(userRepositiry.findByUsername(adminName))
                .thenReturn(Optional.of(testData.get(adminIndex)));
        when(userRepositiry.findById(id))
                .thenReturn(Optional.of(testData.get(index)));
        when(passwordEncoder.encode(password))
                .thenReturn(realPasswordEncoder.encode(password));
        when(userRepositiry.save(any()))
                .thenAnswer((Answer<Void>) invocation -> {
                    testData.set(index, invocation.getArgument(0));
                    return null;
                });

        userService.changePassword(adminName, Role.ADMIN.getAuthorities(), id, dto);

        assertTrue(realPasswordEncoder.matches(password, testData.get(index).getPassword()));
    }

    @Test
    void changePasswordNotAdminNotTheSame() {
        long id = 1L;
        int index = 0;

        int otherIndex = 1;
        String otherName = "other";

        String password = "secret";

        PasswordDto dto = PasswordDto.builder()
                .oldPassword(oldPassword)
                .password(password)
                .matchingPassword(password)
                .build();

        when(userRepositiry.findByUsername(otherName))
                .thenReturn(Optional.of(testData.get(otherIndex)));
        when(userRepositiry.findById(id))
                .thenReturn(Optional.of(testData.get(index)));

        assertThrows(ForbiddenException.class,
                () -> userService.changePassword(otherName, Role.USER.getAuthorities(), id, dto));
        assertThrows(ForbiddenException.class,
                () -> userService.changePassword(otherName, Role.SENIOR.getAuthorities(), id, dto));
    }

    @Test
    void changePasswordInvalidOldPassword() {
        long id = 1L;
        int index = 0;
        String userName = "user";
        String password = "secret";

        PasswordDto dto = PasswordDto.builder()
                .oldPassword(oldPassword + "more")
                .password(password)
                .matchingPassword(password)
                .build();

        when(userRepositiry.findByUsername(userName))
                .thenReturn(Optional.of(testData.get(index)));
        when(userRepositiry.findById(id))
                .thenReturn(Optional.of(testData.get(index)));
        when(passwordEncoder.matches(oldPassword, testData.get(index).getPassword()))
                .thenReturn(realPasswordEncoder.matches(oldPassword, testData.get(index).getPassword()));

        assertThrows(ForbiddenException.class,
                () -> userService.changePassword(userName, null, id, dto));
    }

    @Test
    void changePasswordNotMatching() {
        long id = 1L;
        int index = 0;
        String userName = "user";
        String password = "secret";

        PasswordDto dto = PasswordDto.builder()
                .oldPassword(oldPassword)
                .password(password)
                .matchingPassword(password + "more")
                .build();

        when(userRepositiry.findByUsername(userName))
                .thenReturn(Optional.of(testData.get(index)));
        when(userRepositiry.findById(id))
                .thenReturn(Optional.of(testData.get(index)));
        when(passwordEncoder.matches(oldPassword, testData.get(index).getPassword()))
                .thenReturn(realPasswordEncoder.matches(oldPassword, testData.get(index).getPassword()));

        assertThrows(BadPasswordException.class,
                () -> userService.changePassword(userName, null, id, dto));
    }

    @Test
    void delete() {
        long id = 1L;
        int index = 0;
        int testDataSizeBefore = testData.size();

        when(userRepositiry.findById(id))
                .thenReturn(Optional.of(testData.get(index)));
        doAnswer((Answer<Void>) invocation -> {
            testData.remove(index);
            return null;
        }).when(userRepositiry).deleteById(id);

        userService.delete(id);

        assertEquals(testDataSizeBefore - 1, testData.size());
    }

    @Test
    void deleteNotFound() {
        long id = 4L;

        when(userRepositiry.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.delete(id));
    }
}