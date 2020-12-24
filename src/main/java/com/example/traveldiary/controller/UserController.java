package com.example.traveldiary.controller;

import com.example.traveldiary.Urls;
import com.example.traveldiary.dto.request.PasswordDto;
import com.example.traveldiary.dto.request.UserDto;
import com.example.traveldiary.dto.response.UserRest;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "user service", description = "the user API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(Urls.Users.FULL)
public interface UserController {
    String USER_ID_PATH_VARIABLE = "/{id}";

    @Operation(summary = "get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserRest.class)))),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content)})
    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    ResponseEntity<List<UserRest>> getList();

    @Operation(summary = "get a user type by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRest.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @GetMapping(USER_ID_PATH_VARIABLE)
    @PreAuthorize("hasAuthority('user:read')")
    ResponseEntity<UserRest> getById(
            @Parameter(
                    name = "id",
                    description = "id  of the user to be obtained. Cannot be null",
                    required = true)
            @PathVariable Long id);

    @Operation(summary = "add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "user created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRest.class))),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content)})
    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    ResponseEntity<UserRest> create(
            @Parameter(
                    description = "the user to add. Cannot be null",
                    required = true,
                    schema = @Schema(implementation = UserDto.class))
            @Valid @RequestBody UserDto userDto);

    @Operation(summary = "update an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRest.class))),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @PutMapping(USER_ID_PATH_VARIABLE)
    @PreAuthorize("hasAuthority('user:write')")
    ResponseEntity<UserRest> update(
            @Parameter(
                    name = "id",
                    description = "id of the user to be updated. Cannot be null.",
                    required = true)
            @PathVariable Long id,
            @Parameter(
                    description = "the user to be updated. Cannot be null.",
                    required = true,
                    schema = @Schema(implementation = UserDto.class))
            @Valid @RequestBody UserDto userDto);

    @Operation(summary = "change a user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @PatchMapping(USER_ID_PATH_VARIABLE + "/" + Urls.Users.Password.PART)
    @PreAuthorize("hasAnyAuthority('user:write', 'user:profile')")
    ResponseEntity<String> changePassword(
            @Parameter(
                    name = "id",
                    description = "id of the user to be changed password. Cannot be null.",
                    required = true)
            @PathVariable Long id,
            @Parameter(
                    description = "password data. Cannot be null.",
                    required = true,
                    schema = @Schema(implementation = PasswordDto.class))
            @Valid @RequestBody PasswordDto passwordDto,
            Authentication authentication);

    @Operation(summary = "deletes a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @DeleteMapping(USER_ID_PATH_VARIABLE)
    @PreAuthorize("hasAuthority('user:write')")
    ResponseEntity<String> delete(
            @Parameter(
                    name = "id",
                    description = "id of the user to be deleted. Cannot be null",
                    required = true)
            @PathVariable Long id);

}
