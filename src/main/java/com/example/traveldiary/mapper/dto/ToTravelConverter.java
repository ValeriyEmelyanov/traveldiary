package com.example.traveldiary.mapper.dto;

import com.example.traveldiary.dto.request.ExpenseRecordRequest;
import com.example.traveldiary.dto.request.TravelRequest;
import com.example.traveldiary.model.ExpenseRecord;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.service.ExpenseTypeService;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class ToTravelConverter implements Converter<TravelRequest, Travel> {

    private final ExpenseTypeService expenseTypeService;

    public ToTravelConverter(ExpenseTypeService expenseTypeService) {
        this.expenseTypeService = expenseTypeService;
    }

    @Override
    public Travel convert(TravelRequest travelRequest) {

        Travel travel = new Travel();

        travel.setStatus(travelRequest.getStatus());
        travel.setTitle(travelRequest.getTitle());
        travel.setStartDate(travelRequest.getStartDate());
        travel.setEndDate(travelRequest.getEndDate());
        travel.setDescription(travelRequest.getDescription());
        travel.setPlanTotalSum(travelRequest.getPlanTotalSum());
        travel.setFactTotalSum(travelRequest.getFactTotalSum());
        travel.setRating(travelRequest.getRating());
        travel.setFavorite(travelRequest.getFavorite());

        List<ExpenseRecord> list = toExpenseRecordList(travelRequest.getExpenses(), travel);
        if (list != null) {
            travel.setExpenses(list);
        }

        return travel;
    }

    private List<ExpenseRecord> toExpenseRecordList(List<ExpenseRecordRequest> requestList,
                                                    Travel travel) {
        if (requestList == null) {
            return null;
        }

        List<ExpenseRecord> list = new ArrayList<>(requestList.size());
        for (ExpenseRecordRequest expenseRecordRequest : requestList) {
            list.add(toExpenseRecord(expenseRecordRequest, travel));
        }

        return list;
    }

    private ExpenseRecord toExpenseRecord(ExpenseRecordRequest expenseRecordRequest,
                                          Travel travel) {
        if (expenseRecordRequest == null) {
            return null;
        }

        ExpenseRecord expenseRecord = new ExpenseRecord();

        expenseRecord.setTravel(travel);
        expenseRecord.setExpenseType(expenseTypeService.getById(expenseRecordRequest.getExpenseTypeId()));
        expenseRecord.setRecNo(expenseRecordRequest.getRecNo());
        expenseRecord.setComment(expenseRecordRequest.getComment());
        expenseRecord.setPlanSum(expenseRecordRequest.getPlanSum());
        expenseRecord.setFactSum(expenseRecordRequest.getFactSum());

        return expenseRecord;
    }
}
