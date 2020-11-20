package com.example.traveldiary.model;

public enum Permission {
    EXPENSE_TYPE_READ("expense_type:read"),
    EXPENSE_TYPE_WRITE("expense_type:write"),
    TRAVEL_READ("travel:read"),
    TRAVEL_WRITE("travel:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
