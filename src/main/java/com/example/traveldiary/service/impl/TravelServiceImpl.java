package com.example.traveldiary.service.impl;

import com.example.traveldiary.exception.BadRequestException;
import com.example.traveldiary.exception.ErrorMessages;
import com.example.traveldiary.exception.ForbiddenException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.TravelRepository;
import com.example.traveldiary.service.TravelService;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Travel> getList() {
        return travelRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Travel getById(Long id) {
        if (id == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }
        return travelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND.getErrorMessage()));
    }

    @Transactional
    @Override
    public Travel save(Travel travel, String username) {
        return save(null, travel, username, false);
    }

    @Transactional
    @Override
    public Travel update(Long id, Travel travel, String username) {
        if (id == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }
        return save(id, travel, username, true);
    }

    private Travel save(Long id, Travel travel, String username, boolean isUpdate) {
        if (travel == null) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST.getErrorMessage());
        }

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
    public void delete(Long id, String username) {
        Travel travel = getById(id);
        User user = userService.getByUsername(username);
        if (!user.equals(travel.getUser())) {
            throw new ForbiddenException(ErrorMessages.WRONG_USER.getErrorMessage());
        }

        travelRepository.deleteById(id);
    }
}
