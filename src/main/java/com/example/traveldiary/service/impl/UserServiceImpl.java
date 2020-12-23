package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.intermediate.PasswordData;
import com.example.traveldiary.exception.BadPasswordException;
import com.example.traveldiary.exception.BadRequestException;
import com.example.traveldiary.exception.ErrorMessages;
import com.example.traveldiary.exception.ForbiddenException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.Permission;
import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.UserRepository;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepositiry;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepositiry,
                           PasswordEncoder passwordEncoder) {
        this.userRepositiry = userRepositiry;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getList() {
        return userRepositiry.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User getById(Long id) {
        if (id == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }
        return userRepositiry.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND.getErrorMessage()));
    }

    @Transactional(readOnly = true)
    @Override
    public User getByUsername(String username) {
        if (username == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }
        return userRepositiry.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND.getErrorMessage()));
    }

    @Transactional
    @Override
    public void save(User user) {
        save(null, user, false);
    }

    @Transactional
    @Override
    public void update(Long id, User user) {
        if (id == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }
        save(id, user, true);
    }

    private void save(Long id, User user, boolean isUpdate) {
        if (user == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }

        if (isUpdate) {
            User userSaved = getById(id);
            user.setId(id);
            user.setCreated(userSaved.getCreated());
            user.setTravels(userSaved.getTravels());
            user.setLastActivity(userSaved.getLastActivity());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepositiry.save(user);
    }

    @Transactional
    @Override
    public void changePassword(
            String username,
            Collection<? extends GrantedAuthority> authorities,
            Long id,
            PasswordData passwordData) {

        if (passwordData == null || passwordData.getPassword() == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }

        User currentUser = getByUsername(username);
        User user = getById(id);
        if (!currentUser.equals(user)) {
            boolean hasPermissionUserWrite = authorities.stream()
                    .anyMatch(grantedAuthority
                            -> grantedAuthority.getAuthority().equals(Permission.USER_WRITE.getPermission()));
            if (!hasPermissionUserWrite) {
                throw new ForbiddenException(ErrorMessages.NO_PERMISSIONS.getErrorMessage());
            }
        } else if (passwordData.getOldPassword() == null
                || !passwordEncoder.matches(passwordData.getOldPassword(), user.getPassword())) {
            throw new ForbiddenException(ErrorMessages.WRONG_OLD_PASSWORD.getErrorMessage());
        }

        if (!passwordData.getPassword().equals(passwordData.getMatchingPassword())) {
            throw new BadPasswordException(ErrorMessages.BAD_PASSWORD_NOT_MATCHING.getErrorMessage());
        }

        user.setPassword(passwordEncoder.encode(passwordData.getPassword()));

        userRepositiry.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        getById(id);
        userRepositiry.deleteById(id);
    }
}
