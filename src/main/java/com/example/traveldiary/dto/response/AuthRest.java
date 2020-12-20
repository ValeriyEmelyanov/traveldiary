package com.example.traveldiary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthRest {

    @Schema(description = "access token",
            example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjA5NDQ4NDAwfQ."
                    + "1aYAzg_s1pqz7kRwKzduNiG953mv5rcpYfnUq6OXzY1z_yxZaKnF0ED9Eoq2M6J6kwAoGlcOoX9Pzhlrqmzt6w",
            required = true)
    private final String token;

}
