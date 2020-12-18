package com.example.traveldiary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ExpenseTypeDto {

    @Schema(
            description = "name of the expense type",
            example = "Transort",
            required = true)
    private String name;

}
