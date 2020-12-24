package com.example.traveldiary.controller;

import com.example.traveldiary.Urls;
import com.example.traveldiary.aop.LastActivity;
import com.example.traveldiary.dto.request.TravelDto;
import com.example.traveldiary.dto.response.TravelRest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Tag(name = "travel service", description = "the Travel API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(Urls.Travels.FULL)
public interface TravelController {
    String TRAVEL_ID_PATH_VARIABLE = "/{id}";

    @Operation(summary = "get all travels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TravelRest.class)))),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content)})
    @LastActivity
    @GetMapping
    @PreAuthorize("hasAuthority('travel:read')")
    ResponseEntity<List<TravelRest>> getList();

    @Operation(summary = "get a travel by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TravelRest.class))),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @LastActivity
    @GetMapping(TRAVEL_ID_PATH_VARIABLE)
    @PreAuthorize("hasAuthority('travel:read')")
    ResponseEntity<TravelRest> getById(
            @Parameter(
                    name = "id",
                    description = "id  of the travel to be obtained. Cannot be null",
                    required = true)
            @PathVariable Long id);

    @Operation(summary = "add a new travel type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "expense type created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TravelRest.class))),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content)})
    @LastActivity
    @PostMapping
    @PreAuthorize("hasAuthority('travel:write')")
    ResponseEntity<TravelRest> create(
            @Parameter(
                    description = "the travel to add. Cannot be null",
                    required = true,
                    schema = @Schema(implementation = TravelDto.class))
            @Valid @RequestBody TravelDto travelDto,
            Principal principal);

    @Operation(summary = "update an existing travel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TravelRest.class))),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @LastActivity
    @PutMapping(TRAVEL_ID_PATH_VARIABLE)
    @PreAuthorize("hasAuthority('travel:write')")
    ResponseEntity<TravelRest> update(
            @Parameter(
                    name = "id",
                    description = "id of the travel to be updated. Cannot be null.",
                    required = true)
            @PathVariable Long id,
            @Parameter(
                    description = "the travel to be updated. Cannot be null.",
                    required = true,
                    schema = @Schema(implementation = TravelDto.class))
            @Valid @RequestBody TravelDto travelDto,
            Principal principal);

    @Operation(summary = "deletes a travel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @LastActivity
    @DeleteMapping(TRAVEL_ID_PATH_VARIABLE)
    @PreAuthorize("hasAuthority('travel:write')")
    ResponseEntity<String> delete(
            @Parameter(
                    name = "id",
                    description = "id of the travel to be deleted. Cannot be null",
                    required = true)
            @PathVariable Long id,
            Principal principal);
}
