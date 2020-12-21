package com.example.traveldiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ExpenseTypeDto {

    @Schema(description = "name of the expense type",
            example = "Transort",
            required = true)
    private final String name;

}
