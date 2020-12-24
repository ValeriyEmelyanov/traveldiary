package com.example.traveldiary.service.impl;

import com.example.traveldiary.exception.ErrorMessages;
import com.example.traveldiary.model.User;
import com.example.traveldiary.model.UserLastActivity;
import com.example.traveldiary.repository.UserLastActivityRepository;
import com.example.traveldiary.service.UserLastActivityService;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserLastActivityServiceImpl implements UserLastActivityService {
    private final UserService userService;
    private final UserLastActivityRepository lastActivityRepository;

    @Autowired
    public UserLastActivityServiceImpl(UserService userService,
                                       UserLastActivityRepository lastActivityRepository) {
        this.userService = userService;
        this.lastActivityRepository = lastActivityRepository;
    }

    @Transactional
    @Override
    public void save(String username, LocalDateTime timestamp, String description) {
        Assert.notNull(username, ErrorMessages.NULL_USERNAME.getErrorMessage());
        Assert.notNull(timestamp, ErrorMessages.NULL_TIMESTAMP.getErrorMessage());
        Assert.notNull(description, ErrorMessages.NULL_DESCRIPTION.getErrorMessage());

        User user = userService.getByUsername(username);

        Optional<UserLastActivity> optional = lastActivityRepository.findByUserId(user.getId());
        UserLastActivity lastActivity;
        if (optional.isPresent()) {
            lastActivity = optional.get();
            lastActivity.setLastActivity(timestamp);
            lastActivity.setDescription(description);
        } else {
            lastActivity = UserLastActivity.builder()
                    .user(user)
                    .lastActivity(timestamp)
                    .description(description)
                    .build();
        }

        lastActivityRepository.save(lastActivity);
    }

}
