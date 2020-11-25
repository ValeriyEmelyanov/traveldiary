package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.ExpenseRecordDto;
import com.example.traveldiary.dto.TravelDto;
import com.example.traveldiary.exception.BadRequestException;
import com.example.traveldiary.exception.ForbiddenException;
import com.example.traveldiary.exception.NotFoundException;
import com.example.traveldiary.model.ExpenseRecord;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.model.User;
import com.example.traveldiary.repository.TravelRepository;
import com.example.traveldiary.service.ExpenseTypeService;
import com.example.traveldiary.service.TravelService;
import com.example.traveldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TravelServiceImpl implements TravelService {
    private final TravelRepository travelRepository;
    private final ExpenseTypeService expenseTypeService;
    private final UserService userService;

    @Autowired
    public TravelServiceImpl(TravelRepository travelRepository, ExpenseTypeService expenseTypeService, UserService userService) {
        this.travelRepository = travelRepository;
        this.expenseTypeService = expenseTypeService;
        this.userService = userService;
    }

    @Override
    public List<Travel> getList() {
        return travelRepository.findAll();
    }

    @Override
    public Travel getById(Long id) {
        if (id == null) {
            throw new BadRequestException();
        }
        return travelRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void save(TravelDto travelDto, String username) {
        save(null, travelDto, username, false);
    }

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

        Travel travel = null;
        if (isUpdate) {
            travel = getById(id);
            if (!user.equals(travel.getUser())) {
                throw new ForbiddenException();
            }
        } else {
            travel = new Travel();
            travel.setUser(user);
        }
        travel.setStatus(travelDto.getStatus());
        travel.setTitle(travelDto.getTitle());
        travel.setStartDate(travelDto.getStartDate());
        travel.setEndDate(travelDto.getEndDate());
        travel.setDescription(travelDto.getDescription());
        travel.setPlanTotalSum(travelDto.getPlanTotalSum());
        travel.setFactTotalSum(travelDto.getFactTotalSum());
        travel.setRating(travelDto.getRating());
        travel.setFavorite(travelDto.getFavorite());

        List<ExpenseRecordDto> expensesDto = travelDto.getExpenses();
        if (expensesDto != null) {
            int expensesSize = expensesDto.size();
            if (expensesSize > 0) {
                List<ExpenseRecord> expenses = new ArrayList<>(expensesSize);
                for (ExpenseRecordDto recordDto : expensesDto) {
                    ExpenseRecord record = new ExpenseRecord();
                    record.setRecNo(recordDto.getRecNo());
                    record.setExpenseType(expenseTypeService.getById(recordDto.getExpenseTypeId()));
                    record.setComment(recordDto.getComment());
                    record.setPlanSum(recordDto.getPlanSum());
                    record.setFactSum(recordDto.getFactSum());
                    record.setTravel(travel);
                    expenses.add(record);
                }
                if (isUpdate) {
                    travel.getExpenses().clear();
                    travel.getExpenses().addAll(expenses);
                } else {
                    travel.setExpenses(expenses);
                }
            }
        }

        travelRepository.save(travel);
    }

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
