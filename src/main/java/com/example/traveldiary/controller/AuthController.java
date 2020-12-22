package com.example.traveldiary.controller;

import com.example.traveldiary.Urls;
import com.example.traveldiary.dto.request.UserLoginDto;
import com.example.traveldiary.dto.response.AuthRest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Tag(name = "auth service")
@RequestMapping(Urls.Auth.FULL)
public interface AuthController {
    @Operation(summary = "user authentication",
            description = "get the access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AuthRest.class)))),
            @ApiResponse(responseCode = "400", description = "bad request", content = @Content)})
    @PostMapping
    ResponseEntity<AuthRest> auth(@Valid @RequestBody UserLoginDto loginDto);
}
