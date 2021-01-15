package com.example.traveldiary.mapper.model;

import com.example.traveldiary.dto.response.UserLastActivityResponse;
import com.example.traveldiary.dto.response.UserResponse;
import com.example.traveldiary.model.User;
import com.example.traveldiary.model.UserLastActivity;
import org.springframework.core.convert.converter.Converter;

public class ToUserResponseConverter implements Converter<User, UserResponse> {
    @Override
    public UserResponse convert(User user) {
        UserLastActivityResponse lastActivityRest = null;
        if (user.getLastActivity() != null) {
            lastActivityRest = toUserLastActivityResponse(user.getLastActivity());
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .created(user.getCreated())
                .modified(user.getModified())
                .enabled(user.getEnabled())
                .roles(user.getRoles())
                .lastActivity(lastActivityRest)
                .build();
    }

    private UserLastActivityResponse toUserLastActivityResponse(UserLastActivity userLastActivity) {
        return UserLastActivityResponse.builder()
                .lastActivity(userLastActivity.getLastActivity())
                .description(userLastActivity.getDescription())
                .build();
    }
}
