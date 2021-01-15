package com.example.traveldiary.mapper.dto;

import com.example.traveldiary.dto.intermediate.PasswordDto;
import com.example.traveldiary.dto.request.PasswordRequest;
import org.springframework.core.convert.converter.Converter;

public class ToPasswordDtoConverter implements Converter<PasswordRequest, PasswordDto> {
    @Override
    public PasswordDto convert(PasswordRequest passwordRequest) {
        return PasswordDto.builder()
                .oldPassword(passwordRequest.getOldPassword())
                .password(passwordRequest.getPassword())
                .matchingPassword(passwordRequest.getMatchingPassword())
                .build();
    }
}
