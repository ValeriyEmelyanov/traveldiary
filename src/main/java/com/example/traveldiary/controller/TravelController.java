package com.example.traveldiary.controller;

import com.example.traveldiary.dto.TravelDto;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
@RequestMapping("/api/v1/travel")
public class TravelController {
    private TravelService travelService;

    @Autowired
    public void setTravelService(TravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Travel>> getList() {
        return ResponseEntity.ok(travelService.getList());
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<String> create(@RequestBody TravelDto travelDto) {
        if (travelDto == null) {
            return ResponseEntity.badRequest().build();
        }

        travelService.save(travelDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody TravelDto travelDto) {
        if (travelDto == null || travelDto.getId() == null) {
            ResponseEntity.badRequest().build();
        }

        Travel travel = travelService.getById(travelDto.getId());
        if (travel == null) {
            return ResponseEntity.notFound().build();
        }

        travelService.save(travelDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Travel travel = travelService.getById(id);
        if (travel == null) {
            return ResponseEntity.notFound().build();
        }

        travelService.delete(id);

        return ResponseEntity.ok().build();
    }
}