package com.example.traveldiary.service;


import com.example.traveldiary.dto.intermediate.PasswordData;
import com.example.traveldiary.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface UserService {
    List<User> getList();

    User getById(Long id);

    User getByUsername(String username);

    User save(User user);

    User update(Long id, User user);

    void changePassword(
            String username,
            Collection<? extends GrantedAuthority> authorities,
            Long id,
            PasswordData passwordData);

    void delete(Long id);
}
