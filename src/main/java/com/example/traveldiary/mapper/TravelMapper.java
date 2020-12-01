package com.example.traveldiary.mapper;

import com.example.traveldiary.dto.ExpenseRecordDto;
import com.example.traveldiary.dto.TravelDto;
import com.example.traveldiary.model.ExpenseRecord;
import com.example.traveldiary.model.Travel;
import com.example.traveldiary.service.ExpenseTypeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ExpenseTypeService.class})
public interface TravelMapper {

    Travel toTravel(TravelDto travelDto);

    void updateTravel(TravelDto travelDto, @MappingTarget Travel travel);

    @Mapping(source = "expenseTypeId", target = "expenseType")
    ExpenseRecord toExpenseRecord(ExpenseRecordDto expenseRecordDto);

    List<ExpenseRecord> toExpenseRecordList(List<ExpenseRecordDto> list);

}
