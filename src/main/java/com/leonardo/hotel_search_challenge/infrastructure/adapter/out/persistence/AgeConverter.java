package com.leonardo.hotel_search_challenge.infrastructure.adapter.out.persistence;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
@Component
public class AgeConverter implements AttributeConverter<List<Integer>, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<Integer> ages) {
        if(ages == null || ages.isEmpty()) {
            return "";
        }
        return ages
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public List<Integer> convertToEntityAttribute(String serializedAges) {
        if(serializedAges == null || serializedAges.isBlank()) {
            return List.of();
        }
        return Arrays
                .stream(serializedAges.split(SEPARATOR))
                .map(String::trim)
                .map(Integer::valueOf)
                .toList();
    }
}
