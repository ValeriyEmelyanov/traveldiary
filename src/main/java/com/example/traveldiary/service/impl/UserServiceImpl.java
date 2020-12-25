package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.intermediate.PasswordData;
import com.example.traveldiary.exception.BadPasswordException;
import com.example.traveldiary.exception.BadRequestException;
import com.example.traveldiary.exception.ErrorMessages;
import com.example.traveldiary.exception.ForbiddenException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.exception.UsernameAlreadyTakenException;
import com.example.traveldiary.model.Permission;
import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.UserRepository;
import com.example.traveldiary.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
    @NonNull
    public List<User> getList() {
        log.info("Requested User list");
        return userRepositiry.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    @NonNull
    public User getById(@NonNull Long id) {
        Assert.notNull(id, ErrorMessages.NULL_USER_ID.getErrorMessage());

        log.info("Requested the User with id: {}", id);
        return userRepositiry.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND.getErrorMessage()));
    }

    @Transactional(readOnly = true)
    @Override
    @NonNull
    public User getByUsername(@NonNull String username) {
        Assert.notNull(username, ErrorMessages.NULL_USERNAME.getErrorMessage());

        log.info("Requested the User with username: {}", username);
        return userRepositiry.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND.getErrorMessage()));
    }

    @Transactional
    @Override
    @NonNull
    public User save(@NonNull User user) {
        Assert.notNull(user, ErrorMessages.NULL_USER_OBJECT.getErrorMessage());

        User saved = save(null, user, false);
        log.info("Saved a new User with id: {}", saved.getId());
        return saved;
    }

    @Transactional
    @Override
    @NonNull
    public User update(@NonNull Long id, @NonNull User user) {
        Assert.notNull(id, ErrorMessages.NULL_USER_ID.getErrorMessage());
        Assert.notNull(user, ErrorMessages.NULL_USER_OBJECT.getErrorMessage());

        User updated = save(id, user, true);
        log.info("Updated the User with id: {}", id);
        return updated;
    }

    private User save(Long id, User user, boolean isUpdate) {
        if (isUpdate) {
            User userSaved = getById(id);
            user.setId(id);
            user.setCreated(userSaved.getCreated());
            user.setTravels(userSaved.getTravels());
            user.setLastActivity(userSaved.getLastActivity());
        } else {
            Optional<User> byUsername = userRepositiry.findByUsername(user.getUsername());
            if (byUsername.isPresent()) {
                throw new UsernameAlreadyTakenException(
                        ErrorMessages.USERNAME_ALREADY_TAKEN.getErrorMessage());
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepositiry.save(user);
    }

    @Transactional
    @Override
    public void changePassword(
            @NonNull String username,
            @NonNull Collection<? extends GrantedAuthority> authorities,
            @NonNull Long id,
            @NonNull PasswordData passwordData) {
        Assert.notNull(username, ErrorMessages.NULL_USERNAME.getErrorMessage());
        Assert.notNull(authorities, ErrorMessages.NULL_USER_AUTHORITIES.getErrorMessage());
        Assert.notNull(id, ErrorMessages.NULL_USER_ID.getErrorMessage());
        Assert.notNull(passwordData, ErrorMessages.NULL_PASSWORD_DATA.getErrorMessage());

        if (passwordData.getPassword() == null) {
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
        log.info("Changed a password for the User with id {}: ", id);
    }

    @Transactional
    @Override
    public void delete(@NonNull Long id) {
        Assert.notNull(id, ErrorMessages.NULL_USER_ID.getErrorMessage());

        getById(id);
        userRepositiry.deleteById(id);
        log.info("Deleted the User with id: {}", id);
    }
}
