package com.example.traveldiary.controller;

import com.example.traveldiary.dto.request.UserLoginDto;
import com.example.traveldiary.dto.response.AuthResponse;
import com.example.traveldiary.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth service", description = "")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "user authentication",
            description = "get the access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AuthResponse.class)))),
            @ApiResponse(responseCode = "400", description = "bad request", content = @Content)})
    @PostMapping
    public ResponseEntity<AuthResponse> auth(@RequestBody UserLoginDto loginDto) {
        return ResponseEntity.ok(
                new AuthResponse(authService.auth(
                        loginDto.getUsername(),
                        loginDto.getPassword())));
    }
}
