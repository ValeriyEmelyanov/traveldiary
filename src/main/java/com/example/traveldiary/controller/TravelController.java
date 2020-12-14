package com.example.traveldiary.controller;

import com.example.traveldiary.aop.LastActivity;
import com.example.traveldiary.dto.TravelDto;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.service.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "travel service", description = "the Travel API")
@SecurityRequirement(name = "BasicAuth")
@RestController
@RequestMapping("/api/v1/travels")
public class TravelController {
    private final TravelService travelService;

    @Autowired
    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @Operation(summary = "get all travels", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Travel.class)))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content)})
    @LastActivity
    @GetMapping
    @PreAuthorize("hasAuthority('travel:read')")
    public ResponseEntity<List<Travel>> getList() {
        return ResponseEntity.ok(travelService.getList());
    }

    @Operation(summary = "get a travel by id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Travel.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @LastActivity
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('travel:read')")
    public ResponseEntity<Travel> getById(
            @Parameter(
                    name = "id",
                    description = "id  of the travel to be obtained. Cannot be null",
                    required = true)
            @PathVariable Long id) {
        Travel travel = travelService.getById(id);
        return ResponseEntity.ok(travel);
    }

    @Operation(summary = "add a new travel type", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "expense type created", content = @Content),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content)})
    @LastActivity
    @PostMapping
    @PreAuthorize("hasAuthority('travel:write')")
    public ResponseEntity<String> create(
            @Parameter(
                    description = "the travel to add. Cannot be null",
                    required = true,
                    schema = @Schema(implementation = TravelDto.class))
            @RequestBody TravelDto travelDto,
            Principal principal) {
        travelService.save(travelDto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "update an existing travel", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @LastActivity
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('travel:write')")
    public ResponseEntity<String> update(
            @Parameter(
                    name = "id",
                    description = "id of the travel to be updated. Cannot be null.",
                    required = true)
            @PathVariable Long id,
            @Parameter(
                    description = "the travel to be updated. Cannot be null.",
                    required = true,
                    schema = @Schema(implementation = TravelDto.class))
            @RequestBody TravelDto travelDto,
            Principal principal) {
        travelService.update(id, travelDto, principal.getName());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "deletes a travel", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @LastActivity
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('travel:write')")
    public ResponseEntity<String> delete(
            @Parameter(
                    name = "id",
                    description = "id of the travel to be deleted. Cannot be null",
                    required = true)
            @PathVariable Long id,
            Principal principal) {
        travelService.delete(id, principal.getName());
        return ResponseEntity.ok().build();
    }
}
