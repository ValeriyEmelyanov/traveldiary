package com.example.traveldiary.mapper.model;

import com.example.traveldiary.dto.response.ExpenseRecordRest;
import com.example.traveldiary.dto.response.TravelRest;
import com.example.traveldiary.model.ExpenseRecord;
import com.example.traveldiary.model.Travel;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class ToTravelRestConverter implements Converter<Travel, TravelRest> {
    private final ToExpenseTypeRestConverter toExpenseTypeRestConverter;

    public ToTravelRestConverter(ToExpenseTypeRestConverter toExpenseTypeRestConverter) {
        this.toExpenseTypeRestConverter = toExpenseTypeRestConverter;
    }

    @Override
    public TravelRest convert(Travel travel) {
        List<ExpenseRecordRest> restList = toExpenseRecordRestList(travel.getExpenses());

        return TravelRest.builder()
                .id(travel.getId())
                .status(travel.getStatus())
                .title(travel.getTitle())
                .startDate(travel.getStartDate())
                .endDate(travel.getEndDate())
                .description(travel.getDescription())
                .planTotalSum(travel.getPlanTotalSum())
                .factTotalSum(travel.getFactTotalSum())
                .rating(travel.getRating())
                .favorite(travel.getFavorite())
                .created(travel.getCreated())
                .modified(travel.getModified())
                .user(travel.getUser() != null ? travel.getUser().getUsername() : null)
                .expenses(restList)
                .build();
    }

    private List<ExpenseRecordRest> toExpenseRecordRestList(List<ExpenseRecord> list) {
        if (list == null) {
            return null;
        }

        List<ExpenseRecordRest> restList = new ArrayList<>();
        for (ExpenseRecord record : list) {
            restList.add(toExpenseRecordRest(record));
        }

        return restList;
    }

    private ExpenseRecordRest toExpenseRecordRest(ExpenseRecord record) {
        if (record == null) {
            return null;
        }

        return ExpenseRecordRest.builder()
                .id(record.getId())
                .recNo(record.getRecNo())
                .expenseType(toExpenseTypeRestConverter.convert(record.getExpenseType()))
                .comment(record.getComment())
                .planSum(record.getPlanSum())
                .factSum(record.getFactSum())
                .build();
    }
}
