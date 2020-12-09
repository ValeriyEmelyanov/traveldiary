package com.example.traveldiary.security;

import com.example.traveldiary.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private final boolean enabled;
    private final Set<SimpleGrantedAuthority> authorities;

    public UserDetailsImpl(String username, String password, boolean enabled, Set<SimpleGrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static UserDetails fromUser(User user) {
        return new UserDetailsImpl(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getRoles().stream()
                        .flatMap(role -> role.getAuthorities().stream())
                        .collect(Collectors.toSet())
        );
    }
}
