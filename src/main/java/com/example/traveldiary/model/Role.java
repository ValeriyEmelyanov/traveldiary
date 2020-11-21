package com.example.traveldiary.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of(
            Permission.USER_READ,
            Permission.USER_WRITE,
            Permission.USER_PASSWORD
    )),
    SENIOR(Set.of(
            Permission.EXPENSE_TYPE_READ,
            Permission.EXPENSE_TYPE_WRITE,
            Permission.USER_PASSWORD
    )),
    USER(Set.of(
            Permission.EXPENSE_TYPE_READ,
            Permission.TRAVEL_READ,
            Permission.TRAVEL_WRITE,
            Permission.USER_PASSWORD
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
