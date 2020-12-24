package com.example.traveldiary.service;

import com.example.traveldiary.model.Travel;

import java.util.List;

public interface TravelService {
    List<Travel> getList();

    Travel getById(Long id);

    Travel save(Travel travel, String username);

    Travel update(Long id, Travel travel, String username);

    void delete(Long id, String username);
}
