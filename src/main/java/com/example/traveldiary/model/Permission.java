package com.example.traveldiary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor

@Getter
public enum Permission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_PROFILE("user:profile"),
    EXPENSE_TYPE_READ("expense_type:read"),
    EXPENSE_TYPE_WRITE("expense_type:write"),
    TRAVEL_READ("travel:read"),
    TRAVEL_WRITE("travel:write");

    private final String permission;

}
