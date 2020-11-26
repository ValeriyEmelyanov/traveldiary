package com.example.traveldiary.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordDto {
    private String oldPassword;
    private String password;
    private String matchingPassword;
}
