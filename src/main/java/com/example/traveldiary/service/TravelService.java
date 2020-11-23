package com.example.traveldiary.service;

import com.example.traveldiary.dto.TravelDto;
import com.example.traveldiary.model.Travel;

import java.util.List;

public interface TravelService {
    List<Travel> getList();

    Travel getById(Long id);

    void save(TravelDto travelDto, String username);

    void update(TravelDto travelDto, String username);

    void delete(Long id, String username);
}
