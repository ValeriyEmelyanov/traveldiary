package com.example.traveldiary.config;

import com.example.traveldiary.mapper.PasswordDtoToPasswordDataConverter;
import com.example.traveldiary.mapper.TravelDtoToTravelConverter;
import com.example.traveldiary.mapper.UserDtoToUserConverter;
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
        Set<Converter<?,?>> converters = new HashSet<>();
        converters.add(new TravelDtoToTravelConverter(expenseTypeService));
        converters.add(new UserDtoToUserConverter());
        converters.add(new PasswordDtoToPasswordDataConverter());
        factory.setConverters(converters);
        return factory;
    }
}
