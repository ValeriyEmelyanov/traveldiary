package com.example.traveldiary.controller;

import com.example.traveldiary.Urls;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "play service", description = "the Play API")
@RequestMapping(Urls.Play.FULL)
public interface PlayController {
    @Operation(summary = "Get current time", description = "Get current time")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(mediaType = "text/plain"))})
    @GetMapping(Urls.Play.Now.PART)
    ResponseEntity<String> printDateTime();

    @Operation(summary = "get a greeting", description = "get a greeting corresponding to the time of day")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(mediaType = "text/plain"))})
    @GetMapping({Urls.Play.Greeting.PART, Urls.Play.Greeting.PART + "/{name}"})
    ResponseEntity<String> printGreeting(
            @Parameter(name = "name", description = "the one we greet")
            @PathVariable(required = false) String name);
}
