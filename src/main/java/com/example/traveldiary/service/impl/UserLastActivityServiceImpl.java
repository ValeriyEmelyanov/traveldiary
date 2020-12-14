package com.example.traveldiary.service.impl;

import com.example.traveldiary.model.User;
import com.example.traveldiary.model.UserLastActivity;
import com.example.traveldiary.repository.UserLastActivityRepository;
import com.example.traveldiary.service.UserLastActivityService;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserLastActivityServiceImpl implements UserLastActivityService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserLastActivityRepository lastActivityRepository;

    @Transactional
    @Override
    public void save(String username, LocalDateTime dateTime, String description) {
        User user = userService.getByUsername(username);

        Optional<UserLastActivity> optional = lastActivityRepository.findByUserId(user.getId());
        UserLastActivity lastActivity;
        if (optional.isPresent()) {
            lastActivity = optional.get();
            lastActivity.setLastActivity(dateTime);
            lastActivity.setDescription(description);
        } else {
            lastActivity = UserLastActivity.builder()
                    .user(user)
                    .lastActivity(dateTime)
                    .description(description)
                    .build();
        }

        lastActivityRepository.save(lastActivity);
    }

}
