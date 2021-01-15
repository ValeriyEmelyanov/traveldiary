package com.example.traveldiary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseTypeResponse {

    @Schema(description = "Unique identifier",
            example = "1")
    private final Long id;

    @Schema(description = "name of the expense type",
            example = "Transort")
    private final String name;
}
