package com.example.traveldiary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExpenseTypeDto {

    @Schema(
            description = "name of the expense type",
            example = "Transort",
            required = true)
    private String name;

}
