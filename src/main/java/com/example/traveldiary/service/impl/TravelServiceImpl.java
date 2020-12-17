package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.TravelDto;
import com.example.traveldiary.exception.BadRequestException;
import com.example.traveldiary.exception.ForbiddenException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.mapper.TravelMapper;
import com.example.traveldiary.model.ExpenseRecord;
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
    private final TravelMapper travelMapper;

    @Autowired
    public TravelServiceImpl(TravelRepository travelRepository,
                             UserService userService,
                             TravelMapper travelMapper) {
        this.travelRepository = travelRepository;
        this.userService = userService;
        this.travelMapper = travelMapper;
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
            throw new BadRequestException();
        }
        return travelRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    @Override
    public void save(TravelDto travelDto, String username) {
        save(null, travelDto, username, false);
    }

    @Transactional
    @Override
    public void update(Long id, TravelDto travelDto, String username) {
        if (id == null) {
            throw new BadRequestException();
        }
        save(id, travelDto, username, true);
    }

    private void save(Long id, TravelDto travelDto, String username, boolean isUpdate) {
        if (travelDto == null) {
            throw new BadRequestException();
        }

        User user = userService.getByUsername(username);

        Travel travel;
        if (isUpdate) {
            travel = getById(id);
            if (!user.equals(travel.getUser())) {
                throw new ForbiddenException();
            }
            travelMapper.updateTravel(travelDto, travel);
        } else {
            travel = travelMapper.toTravel(travelDto);
            travel.setUser(user);
        }

        if (travel.getExpenses() != null) {
            for (ExpenseRecord expenseRecord : travel.getExpenses()) {
                expenseRecord.setTravel(travel);
            }
        }

        travelRepository.save(travel);
    }

    @Transactional
    @Override
    public void delete(Long id, String username) {
        Travel travel = getById(id);
        User user = userService.getByUsername(username);
        if (!user.equals(travel.getUser())) {
            throw new ForbiddenException();
        }

        travelRepository.deleteById(id);
    }
}
