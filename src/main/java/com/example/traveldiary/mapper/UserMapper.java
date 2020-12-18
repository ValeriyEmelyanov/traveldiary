package com.example.traveldiary.mapper;

import com.example.traveldiary.dto.request.UserDto;
import com.example.traveldiary.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toUser(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    void updateUser(UserDto userDto, @MappingTarget User user);

}
