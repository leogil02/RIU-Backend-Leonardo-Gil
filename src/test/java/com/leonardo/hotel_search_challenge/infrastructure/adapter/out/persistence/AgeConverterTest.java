package com.leonardo.hotel_search_challenge.infrastructure.adapter.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AgeConverterTest {

    private final AgeConverter converter = new AgeConverter();

    @Test
    @DisplayName("Debe serializar una lista de ages como un String con una separación por comas entre elementos")
    void should_serialize_ages_as_comma_separated_string(){
        String result = converter.convertToDatabaseColumn(List.of(30,29,1,3,3));
        assertEquals("30,29,1,3,3", result);
    }

    @Test
    @DisplayName("Debe deserializar un string separado por comas a una lista de enteros (ages)")
    void should_deserialize_comma_separated_string_as_ages() {
        List<Integer> result = converter.convertToEntityAttribute("30,29,1,3,3");
        assertEquals(List.of(30,29,1,3,3), result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Debe devolver un string vacío cuando se pasa una lista de enteros vacía o nula")
    void should_return_empty_string_when_ages_list_is_empty_or_null(List<Integer> ages){
        String result = converter.convertToDatabaseColumn(ages);
        assertEquals("", result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Debe devolver una lista vacía cuando se pasa string vacío o nulo")
    void should_return_empty_list_when_string_is_empty_or_null(String serializedAges){
        List<Integer> result = converter.convertToEntityAttribute(serializedAges);
        assertEquals(List.of(), result);
    }

}