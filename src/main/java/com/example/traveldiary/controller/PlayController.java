package com.example.traveldiary.controller;

import com.example.traveldiary.service.DateTimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "play service", description = "the Play API")
@RestController
@RequestMapping("/api/v1/play")
public class PlayController {
    private DateTimeService dateTimeService;

    @Autowired
    public PlayController(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    @Operation(summary = "Get current time", description = "Get current time")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(mediaType = "text/plain"))})
    @GetMapping("/now")
    public ResponseEntity<String> printDateTime() {
        return new ResponseEntity<>(
                dateTimeService.getNow(),
                HttpStatus.OK);
    }

    @Operation(summary = "get a greeting", description = "get a greeting corresponding to the time of day")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(mediaType = "text/plain"))})
    @GetMapping({"/greeting", "/greeting/{name}"})
    public ResponseEntity<String> printGreeting(
            @Parameter(name = "name", description = "the one we greet", required = false)
            @PathVariable(required = false) String name) {
        return new ResponseEntity<>(
                dateTimeService.getGreeting(name),
                HttpStatus.OK);
    }

}
