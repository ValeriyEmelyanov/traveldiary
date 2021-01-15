package com.example.traveldiary.config;

import com.example.traveldiary.mapper.dto.ToExpenseTypeConverter;
import com.example.traveldiary.mapper.dto.ToPasswordDtoConverter;
import com.example.traveldiary.mapper.dto.ToTravelConverter;
import com.example.traveldiary.mapper.dto.ToUserConverter;
import com.example.traveldiary.mapper.model.ToExpenseTypeResponseConverter;
import com.example.traveldiary.mapper.model.ToTravelResponseConverter;
import com.example.traveldiary.mapper.model.ToUserResponseConverter;
import com.example.traveldiary.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionConfig {

    private final ExpenseTypeService expenseTypeService;

    @Autowired
    public ConversionConfig(ExpenseTypeService expenseTypeService) {
        this.expenseTypeService = expenseTypeService;
    }

    @Bean
    ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
        Set<Converter<?, ?>> converters = new HashSet<>();

        converters.add(new ToTravelConverter(expenseTypeService));
        converters.add(new ToUserConverter());
        converters.add(new ToPasswordDtoConverter());
        converters.add(new ToExpenseTypeConverter());

        converters.add(new ToUserResponseConverter());
        ToExpenseTypeResponseConverter toExpenseTypeResponseConverter = new ToExpenseTypeResponseConverter();
        converters.add(toExpenseTypeResponseConverter);
        converters.add(new ToTravelResponseConverter(toExpenseTypeResponseConverter));

        factory.setConverters(converters);

        return factory;
    }
}
