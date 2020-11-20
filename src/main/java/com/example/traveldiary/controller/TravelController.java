package com.example.traveldiary.controller;

import com.example.traveldiary.dto.TravelDto;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travel")
public class TravelController {
    private final TravelService travelService;

    @Autowired
    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('travel:read')")
    public ResponseEntity<List<Travel>> getList() {
        return ResponseEntity.ok(travelService.getList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('travel:read')")
    public ResponseEntity<Travel> getById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Travel travel = travelService.getById(id);
        if (travel == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(travel);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('travel:write')")
    public ResponseEntity<String> create(@RequestBody TravelDto travelDto) {
        if (travelDto == null) {
            return ResponseEntity.badRequest().build();
        }

        travelService.save(travelDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('travel:write')")
    public ResponseEntity<String> update(@RequestBody TravelDto travelDto) {
        if (travelDto == null || travelDto.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (travelService.notExists(travelDto.getId())) {
            return ResponseEntity.notFound().build();
        }

        travelService.save(travelDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('travel:write')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        if (travelService.notExists(id)) {
            return ResponseEntity.notFound().build();
        }

        travelService.delete(id);

        return ResponseEntity.ok().build();
    }
}
