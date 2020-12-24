package com.example.traveldiary.mapper.model;

import com.example.traveldiary.dto.response.UserLastActivityRest;
import com.example.traveldiary.dto.response.UserRest;
import com.example.traveldiary.model.User;
import com.example.traveldiary.model.UserLastActivity;
import org.springframework.core.convert.converter.Converter;

public class ToUserRestConverter implements Converter<User, UserRest> {
    @Override
    public UserRest convert(User user) {
        UserLastActivityRest lastActivityRest = null;
        if (user.getLastActivity() != null) {
            lastActivityRest = toUserLastActivityRest(user.getLastActivity());
        }

        return UserRest.builder()
                .id(user.getId())
                .username(user.getUsername())
                .created(user.getCreated())
                .modified(user.getModified())
                .enabled(user.getEnabled())
                .roles(user.getRoles())
                .lastActivity(lastActivityRest)
                .build();
    }

    private UserLastActivityRest toUserLastActivityRest(UserLastActivity userLastActivity) {
        return UserLastActivityRest.builder()
                .lastActivity(userLastActivity.getLastActivity())
                .description(userLastActivity.getDescription())
                .build();
    }
}
