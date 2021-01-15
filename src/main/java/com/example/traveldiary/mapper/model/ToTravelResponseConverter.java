package com.example.traveldiary.mapper.model;

import com.example.traveldiary.dto.response.ExpenseRecordResponse;
import com.example.traveldiary.dto.response.TravelResponse;
import com.example.traveldiary.model.ExpenseRecord;
import com.example.traveldiary.model.Travel;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class ToTravelResponseConverter implements Converter<Travel, TravelResponse> {
    private final ToExpenseTypeResponseConverter toExpenseTypeRestConverter;

    public ToTravelResponseConverter(ToExpenseTypeResponseConverter toExpenseTypeRestConverter) {
        this.toExpenseTypeRestConverter = toExpenseTypeRestConverter;
    }

    @Override
    public TravelResponse convert(Travel travel) {
        List<ExpenseRecordResponse> restList = toExpenseRecordResponseList(travel.getExpenses());

        return TravelResponse.builder()
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

    private List<ExpenseRecordResponse> toExpenseRecordResponseList(List<ExpenseRecord> list) {
        if (list == null) {
            return null;
        }

        List<ExpenseRecordResponse> restList = new ArrayList<>();
        for (ExpenseRecord record : list) {
            restList.add(toExpenseRecordResponse(record));
        }

        return restList;
    }

    private ExpenseRecordResponse toExpenseRecordResponse(ExpenseRecord record) {
        if (record == null) {
            return null;
        }

        return ExpenseRecordResponse.builder()
                .id(record.getId())
                .recNo(record.getRecNo())
                .expenseType(toExpenseTypeRestConverter.convert(record.getExpenseType()))
                .comment(record.getComment())
                .planSum(record.getPlanSum())
                .factSum(record.getFactSum())
                .build();
    }
}
