package com.example.traveldiary.service.impl;

import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.UserLastActivityRepository;
import com.example.traveldiary.service.UserLastActivityService;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

        if (lastActivityRepository.findById(user.getId()).isPresent()) {
            lastActivityRepository.update(user.getId(), LocalDateTime.now(), description);
        } else {
            lastActivityRepository.save(user.getId(), LocalDateTime.now(), description);
        }
    }

}
