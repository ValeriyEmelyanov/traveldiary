package com.example.traveldiary.controller.impl;

import com.example.traveldiary.controller.TravelController;
import com.example.traveldiary.dto.request.TravelDto;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class TravelControllerImpl implements TravelController {
    private final TravelService travelService;
    private final ConversionService conversionService;

    @Autowired
    public TravelControllerImpl(TravelService travelService, ConversionService conversionService) {
        this.travelService = travelService;
        this.conversionService = conversionService;
    }

    @Override
    public ResponseEntity<List<Travel>> getList() {
        return ResponseEntity.ok(travelService.getList());
    }

    @Override
    public ResponseEntity<Travel> getById(Long id) {
        Travel travel = travelService.getById(id);
        return ResponseEntity.ok(travel);
    }

    @Override
    public ResponseEntity<String> create(TravelDto travelDto,
                                         Principal principal) {
        travelService.save(
                conversionService.convert(travelDto, Travel.class),
                principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<String> update(Long id,
                                         TravelDto travelDto,
                                         Principal principal) {
        travelService.update(id,
                conversionService.convert(travelDto, Travel.class),
                principal.getName());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<String> delete(Long id,
                                         Principal principal) {
        travelService.delete(id, principal.getName());
        return ResponseEntity.ok().build();
    }
}
