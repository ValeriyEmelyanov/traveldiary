package com.example.traveldiary.mapper;

import com.example.traveldiary.dto.request.ExpenseRecordDto;
import com.example.traveldiary.dto.request.TravelDto;
import com.example.traveldiary.model.ExpenseRecord;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.service.ExpenseTypeService;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class TravelDtoToTravelConverter implements Converter<TravelDto, Travel> {

    private final ExpenseTypeService expenseTypeService;

    public TravelDtoToTravelConverter(ExpenseTypeService expenseTypeService) {
        this.expenseTypeService = expenseTypeService;
    }

    @Override
    public Travel convert(TravelDto travelDto) {

        Travel travel = new Travel();

        travel.setStatus(travelDto.getStatus());
        travel.setTitle(travelDto.getTitle());
        travel.setStartDate(travelDto.getStartDate());
        travel.setEndDate(travelDto.getEndDate());
        travel.setDescription(travelDto.getDescription());
        travel.setPlanTotalSum(travelDto.getPlanTotalSum());
        travel.setFactTotalSum(travelDto.getFactTotalSum());
        travel.setRating(travelDto.getRating());
        travel.setFavorite(travelDto.getFavorite());

        if (travel.getExpenses() != null) {
            List<ExpenseRecord> list = toExpenseRecordList(travelDto.getExpenses(), travel);
            if (list != null) {
                travel.getExpenses().clear();
                travel.getExpenses().addAll(list);
            } else {
                travel.setExpenses(null);
            }
        } else {
            List<ExpenseRecord> list = toExpenseRecordList(travelDto.getExpenses(), travel);
            if (list != null) {
                travel.setExpenses(list);
            }
        }

        return travel;
    }

    private List<ExpenseRecord> toExpenseRecordList(List<ExpenseRecordDto> dtoList, Travel travel) {
        if (dtoList == null) {
            return null;
        }

        List<ExpenseRecord> list = new ArrayList<>(dtoList.size());
        for (ExpenseRecordDto expenseRecordDto : dtoList) {
            list.add(toExpenseRecord(expenseRecordDto, travel));
        }

        return list;
    }

    private ExpenseRecord toExpenseRecord(ExpenseRecordDto expenseRecordDto, Travel travel) {
        if (expenseRecordDto == null) {
            return null;
        }

        ExpenseRecord expenseRecord = new ExpenseRecord();

        expenseRecord.setTravel(travel);
        expenseRecord.setExpenseType(expenseTypeService.getById(expenseRecordDto.getExpenseTypeId()));
        expenseRecord.setRecNo(expenseRecordDto.getRecNo());
        expenseRecord.setComment(expenseRecordDto.getComment());
        expenseRecord.setPlanSum(expenseRecordDto.getPlanSum());
        expenseRecord.setFactSum(expenseRecordDto.getFactSum());

        return expenseRecord;
    }
}
