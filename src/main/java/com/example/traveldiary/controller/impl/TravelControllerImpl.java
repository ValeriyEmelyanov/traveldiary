package com.example.traveldiary.controller.impl;

import com.example.traveldiary.controller.TravelController;
import com.example.traveldiary.dto.request.TravelRequest;
import com.example.traveldiary.dto.response.TravelResponse;
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
    public ResponseEntity<List<TravelResponse>> getList() {
        return ResponseEntity.ok(travelService.getList()
                .stream()
                .map(e -> conversionService.convert(e, TravelResponse.class))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<TravelResponse> getById(Long id) {
        Travel travel = travelService.getById(id);
        return ResponseEntity.ok(Objects.requireNonNull(
                conversionService.convert(travel, TravelResponse.class)));
    }

    @Override
    public ResponseEntity<TravelResponse> create(TravelRequest travelRequest,
                                                 Principal principal) {
        Travel saved = travelService.save(
                Objects.requireNonNull(conversionService.convert(travelRequest, Travel.class)),
                principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(conversionService.convert(saved, TravelResponse.class));
    }

    @Override
    public ResponseEntity<TravelResponse> update(Long id,
                                                 TravelRequest travelRequest,
                                                 Principal principal) {
        Travel updated = travelService.update(id,
                Objects.requireNonNull(conversionService.convert(travelRequest, Travel.class)),
                principal.getName());
        return ResponseEntity.ok()
                .body(conversionService.convert(updated, TravelResponse.class));
    }

    @Override
    public ResponseEntity<String> delete(Long id,
                                         Principal principal) {
        travelService.delete(id, principal.getName());
        return ResponseEntity.ok().build();
    }
}
