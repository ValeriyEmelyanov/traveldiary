package com.example.traveldiary.controller.impl;

import com.example.traveldiary.controller.TravelController;
import com.example.traveldiary.dto.request.TravelDto;
import com.example.traveldiary.dto.response.TravelRest;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<TravelRest>> getList() {
        return ResponseEntity.ok(travelService.getList()
                .stream()
                .map(e -> conversionService.convert(e, TravelRest.class))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<TravelRest> getById(Long id) {
        Travel travel = travelService.getById(id);
        return ResponseEntity.ok(Objects.requireNonNull(
                conversionService.convert(travel, TravelRest.class)));
    }

    @Override
    public ResponseEntity<TravelRest> create(TravelDto travelDto,
                                             Principal principal) {
        Travel saved = travelService.save(
                Objects.requireNonNull(conversionService.convert(travelDto, Travel.class)),
                principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(conversionService.convert(saved, TravelRest.class));
    }

    @Override
    public ResponseEntity<TravelRest> update(Long id,
                                             TravelDto travelDto,
                                             Principal principal) {
        Travel updated = travelService.update(id,
                Objects.requireNonNull(conversionService.convert(travelDto, Travel.class)),
                principal.getName());
        return ResponseEntity.ok()
                .body(conversionService.convert(updated, TravelRest.class));
    }

    @Override
    public ResponseEntity<String> delete(Long id,
                                         Principal principal) {
        travelService.delete(id, principal.getName());
        return ResponseEntity.ok().build();
    }
}
