package com.example.traveldiary.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Role {
    ADMIN(Set.of(
            Permission.USER_READ,
            Permission.USER_WRITE,
            Permission.USER_PROFILE
    )),
    SENIOR(Set.of(
            Permission.EXPENSE_TYPE_READ,
            Permission.EXPENSE_TYPE_WRITE,
            Permission.USER_PROFILE
    )),
    USER(Set.of(
            Permission.EXPENSE_TYPE_READ,
            Permission.TRAVEL_READ,
            Permission.TRAVEL_WRITE,
            Permission.USER_PROFILE
    ));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
