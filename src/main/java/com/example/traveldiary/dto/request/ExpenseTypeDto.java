package com.example.traveldiary.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class ExpenseTypeDto {

    @Schema(description = "name of the expense type",
            example = "Transort",
            required = true)
    @NotBlank(message = "The field is required")
    @Size(max = 100, message = "Max length is 100")
    private final String name;

    @JsonCreator
    public ExpenseTypeDto(
            @NotBlank(message = "The field is required")
            @Size(max = 100, message = "Max length is 100")
            @JsonProperty("name")
                    String name) {
        this.name = name;
    }
}
