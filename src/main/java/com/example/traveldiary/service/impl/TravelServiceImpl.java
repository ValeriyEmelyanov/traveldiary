package com.example.traveldiary.service.impl;

import com.example.traveldiary.dto.ExpenseRecordDto;
import com.example.traveldiary.dto.TravelDto;
import com.example.traveldiary.model.ExpenseRecord;
import com.example.traveldiary.model.ExpenseType;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.repository.TravelRepository;
import com.example.traveldiary.service.ExpenseTypeService;
import com.example.traveldiary.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TravelServiceImpl implements TravelService {
    private TravelRepository travelRepository;
    private ExpenseTypeService expenseTypeService;

    @Autowired
    public void setTravelRepository(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @Override
    public List<Travel> getList() {
        return travelRepository.findAll();
    }

    @Override
    public Travel getById(Long id) {
        return travelRepository.findById(id).orElse(null);
    }

    @Override
    public void save(TravelDto travelDto) {
        Travel travel = new Travel();
        travel.setId(travelDto.getId());
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
                    record.setId(recordDto.getId());
                    record.setRecNo(recordDto.getRecNo());
                    //TOTO: проверить будет ли так работать.
                    //record.setExpenseType(new ExpenseType(recordDto.getExpenseTypeId()));
                    record.setExpenseType(expenseTypeService.getById(recordDto.getExpenseTypeId()));
                    record.setComment(recordDto.getComment());
                    record.setPlanSum(recordDto.getPlanSum());
                    record.setFactSum(recordDto.getFactPlan());
                    record.setTravel(travel);
                    expenses.add(record);
                }
                travel.setExpenses(expenses);
            }
        }

        travelRepository.save(travel);
    }

    @Override
    public void delete(Long id) {
        travelRepository.deleteById(id);
    }
}
