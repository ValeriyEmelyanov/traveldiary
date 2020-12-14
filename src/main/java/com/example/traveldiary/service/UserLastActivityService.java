package com.example.traveldiary.service;

import java.time.LocalDateTime;

public interface UserLastActivityService {
    void save(String username, LocalDateTime dateTime, String description);
}
