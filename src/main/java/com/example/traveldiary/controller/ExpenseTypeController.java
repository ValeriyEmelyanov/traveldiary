package com.example.traveldiary.controller;

import com.example.traveldiary.Urls;
import com.example.traveldiary.dto.request.ExpenseTypeRequest;
import com.example.traveldiary.dto.response.ExpenseTypeResponse;
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
import java.util.List;

@Tag(name = "expense type service", description = "the Expense Type API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(Urls.ExpenseTypes.FULL)
public interface ExpenseTypeController {
    String EXPENSE_TYPE_ID_PATH_VARIABLE = "/{id}";

    @Operation(summary = "get all expense types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExpenseTypeResponse.class)))),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content)})
    @GetMapping
    @PreAuthorize("hasAuthority('expense_type:read')")
    ResponseEntity<List<ExpenseTypeResponse>> getList();

    @Operation(summary = "get an expense type by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExpenseTypeResponse.class))),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @GetMapping(EXPENSE_TYPE_ID_PATH_VARIABLE)
    @PreAuthorize("hasAuthority('expense_type:read')")
    ResponseEntity<ExpenseTypeResponse> getById(
            @Parameter(
                    name = "id",
                    description = "id  of the expense type to be obtained. Cannot be null",
                    required = true)
            @PathVariable Long id);

    @Operation(summary = "add a new expense type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "expense type created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExpenseTypeResponse.class))),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content)})
    @PostMapping
    @PreAuthorize("hasAuthority('expense_type:write')")
    ResponseEntity<ExpenseTypeResponse> create(
            @Parameter(
                    description = "the expense type to add. Cannot be null",
                    required = true,
                    schema = @Schema(implementation = ExpenseTypeRequest.class))
            @Valid @RequestBody ExpenseTypeRequest expenseTypeRequest);

    @Operation(summary = "update an existing expense type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExpenseTypeResponse.class))),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @PutMapping(EXPENSE_TYPE_ID_PATH_VARIABLE)
    @PreAuthorize("hasAuthority('expense_type:write')")
    ResponseEntity<ExpenseTypeResponse> update(
            @Parameter(
                    name = "id",
                    description = "id of the expense type to be updated. Cannot be null.",
                    required = true)
            @PathVariable Long id,
            @Parameter(
                    description = "the expense type to be updated. Cannot be null.",
                    required = true,
                    schema = @Schema(implementation = ExpenseTypeRequest.class))
            @Valid @RequestBody ExpenseTypeRequest expenseTypeRequest);


    @Operation(summary = "deletes an expense type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @DeleteMapping(EXPENSE_TYPE_ID_PATH_VARIABLE)
    @PreAuthorize("hasAuthority('expense_type:write')")
    ResponseEntity<String> delete(
            @Parameter(
                    name = "id",
                    description = "id of the expense type to be deleted. Cannot be null",
                    required = true)
            @PathVariable Long id);
}
