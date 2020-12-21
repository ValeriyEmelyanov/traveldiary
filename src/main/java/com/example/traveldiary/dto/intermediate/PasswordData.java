package com.example.traveldiary.dto.intermediate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PasswordData {
    private final String oldPassword;
    private final String password;
    private final String matchingPassword;
}
