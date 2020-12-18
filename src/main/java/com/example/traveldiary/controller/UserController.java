package com.example.traveldiary.controller;

import com.example.traveldiary.Urls;
import com.example.traveldiary.dto.request.PasswordDto;
import com.example.traveldiary.dto.request.UserDto;
import com.example.traveldiary.model.User;
import com.example.traveldiary.service.UserService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "user service", description = "the user API")
@SecurityRequirement(name = "BasicAuth")
@RestController
@RequestMapping(Urls.Users.FULL)
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "get all users",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content)})
    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<List<User>> getList() {
        return ResponseEntity.ok(userService.getList());
    }

    @Operation(summary = "get a user type by id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<User> getById(
            @Parameter(
                    name = "id",
                    description = "id  of the user to be obtained. Cannot be null",
                    required = true)
            @PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "add a new user",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "user created", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content)})
    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<String> create(
            @Parameter(
                    description = "the user to add. Cannot be null",
                    required = true,
                    schema = @Schema(implementation = UserDto.class))
            @RequestBody UserDto userDto) {
        userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "update an existing user",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<String> update(
            @Parameter(
                    name = "id",
                    description = "id of the user to be updated. Cannot be null.",
                    required = true)
            @PathVariable Long id,
            @Parameter(
                    description = "the user to be updated. Cannot be null.",
                    required = true,
                    schema = @Schema(implementation = UserDto.class))
            @RequestBody UserDto userDto) {
        userService.update(id, userDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "change a user password",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @PatchMapping("/{id}/" + Urls.Users.Password.PART)
    @PreAuthorize("hasAnyAuthority('user:write', 'user:profile')")
    public ResponseEntity<String> changePassword(
            @Parameter(
                    name = "id",
                    description = "id of the user to be changed password. Cannot be null.",
                    required = true)
            @PathVariable Long id,
            @Parameter(
                    description = "password data. Cannot be null.",
                    required = true,
                    schema = @Schema(implementation = PasswordDto.class))
            @RequestBody PasswordDto passwordDto,
            Authentication authentication) {

        userService.changePassword(
                ((UserDetails) authentication.getPrincipal()).getUsername(),
                authentication.getAuthorities(),
                id,
                passwordDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "deletes a user",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<String> delete(
            @Parameter(
                    name = "id",
                    description = "id of the user to be deleted. Cannot be null",
                    required = true)
            @PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
