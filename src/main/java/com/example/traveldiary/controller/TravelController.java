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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/travels")
public class TravelController {
    private final TravelService travelService;

    @Autowired
    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('travel:read')")
    public ResponseEntity<List<Travel>> getList() {
        return ResponseEntity.ok(travelService.getList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('travel:read')")
    public ResponseEntity<Travel> getById(@PathVariable Long id) {
        Travel travel = travelService.getById(id);
        return ResponseEntity.ok(travel);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('travel:write')")
    public ResponseEntity<String> create(@RequestBody TravelDto travelDto, Principal principal) {
        travelService.save(travelDto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('travel:write')")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @RequestBody TravelDto travelDto,
                                         Principal principal) {
        travelService.update(id, travelDto, principal.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('travel:write')")
    public ResponseEntity<String> delete(@PathVariable Long id, Principal principal) {
        travelService.delete(id, principal.getName());
        return ResponseEntity.ok().build();
    }
}
