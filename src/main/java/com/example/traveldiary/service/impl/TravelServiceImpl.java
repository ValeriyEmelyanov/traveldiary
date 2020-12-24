package com.example.traveldiary.service.impl;

import com.example.traveldiary.exception.ErrorMessages;
import com.example.traveldiary.exception.ForbiddenException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.TravelRepository;
import com.example.traveldiary.service.TravelService;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class TravelServiceImpl implements TravelService {
    private final TravelRepository travelRepository;
    private final UserService userService;

    @Autowired
    public TravelServiceImpl(TravelRepository travelRepository,
                             UserService userService) {
        this.travelRepository = travelRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    @Override
    @NonNull
    public List<Travel> getList() {
        return travelRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    @NonNull
    public Travel getById(@NonNull Long id) {
        Assert.notNull(id, ErrorMessages.NULL_TRAVEL_ID.getErrorMessage());

        return travelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND.getErrorMessage()));
    }

    @Transactional
    @Override
    @NonNull
    public Travel save(@NonNull Travel travel, @NonNull String username) {
        Assert.notNull(travel, ErrorMessages.NULL_TRAVEL_OBJECT.getErrorMessage());
        Assert.notNull(username, ErrorMessages.NULL_USERNAME.getErrorMessage());

        return save(null, travel, username, false);
    }

    @Transactional
    @Override
    @NonNull
    public Travel update(@NonNull Long id, @NonNull Travel travel, @NonNull String username) {
        Assert.notNull(id, ErrorMessages.NULL_TRAVEL_ID.getErrorMessage());
        Assert.notNull(travel, ErrorMessages.NULL_TRAVEL_OBJECT.getErrorMessage());
        Assert.notNull(username, ErrorMessages.NULL_USERNAME.getErrorMessage());

        return save(id, travel, username, true);
    }

    private Travel save(Long id, Travel travel, String username, boolean isUpdate) {
        User user = userService.getByUsername(username);

        if (isUpdate) {
            Travel travelSaved = getById(id);
            if (!user.equals(travelSaved.getUser())) {
                throw new ForbiddenException(ErrorMessages.WRONG_USER.getErrorMessage());
            }
            travel.setId(travelSaved.getId());
            travel.setCreated(travelSaved.getCreated());
            if (travel.getExpenses() == null && travelSaved.getExpenses() != null) {
                travel.setExpenses(List.of());
            }
        }

        travel.setUser(user);

        return travelRepository.save(travel);
    }

    @Transactional
    @Override
    @NonNull
    public void delete(@NonNull Long id, @NonNull String username) {
        Assert.notNull(id, ErrorMessages.NULL_TRAVEL_ID.getErrorMessage());
        Assert.notNull(username, ErrorMessages.NULL_USERNAME.getErrorMessage());

        Travel travel = getById(id);
        User user = userService.getByUsername(username);
        if (!user.equals(travel.getUser())) {
            throw new ForbiddenException(ErrorMessages.WRONG_USER.getErrorMessage());
        }

        travelRepository.deleteById(id);
    }
}
