package com.example.traveldiary.service;

import com.example.traveldiary.dto.TravelDto;
import com.example.traveldiary.model.Travel;

import java.util.List;

public interface TravelService {
    List<Travel> getList();

    Travel getById(Long id);

    boolean notExists(Long id);

    void save(TravelDto travelDto);

    void delete(Long id);
}
