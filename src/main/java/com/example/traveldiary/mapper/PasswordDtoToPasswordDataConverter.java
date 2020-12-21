package com.example.traveldiary.mapper;

import com.example.traveldiary.dto.intermediate.PasswordData;
import com.example.traveldiary.dto.request.PasswordDto;
import org.springframework.core.convert.converter.Converter;

public class PasswordDtoToPasswordDataConverter implements Converter<PasswordDto, PasswordData> {
    @Override
    public PasswordData convert(PasswordDto passwordDto) {
        return PasswordData.builder()
                .oldPassword(passwordDto.getOldPassword())
                .password(passwordDto.getPassword())
                .matchingPassword(passwordDto.getMatchingPassword())
                .build();
    }
}
