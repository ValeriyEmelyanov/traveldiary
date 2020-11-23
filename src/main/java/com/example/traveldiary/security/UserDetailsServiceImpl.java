package com.example.traveldiary.security;

import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.User;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.getByUsername(username);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(String.format("User '%s' doesn't exist", username));
        }
        return UserDetailsImpl.fromUser(user);
    }
}
