package com.leonardo.hotel_search_challenge.domain.model;

import com.leonardo.hotel_search_challenge.domain.exception.DomainValidationException;
import com.leonardo.hotel_search_challenge.domain.shared.GlobalMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static com.leonardo.hotel_search_challenge.TestData.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HotelSearchCountTest {

    //Construcción exitosa

    @Test
    @DisplayName("Debe construirse correctamente con datos válidos")
    void should_create_successfully_with_valid_data(){
        var hotelSearchCount = new HotelSearchCount(SEARCH_ID, HOTEL_SEARCH, COUNT);

        assertAll(
                () -> assertEquals(SEARCH_ID, hotelSearchCount.searchId()),
                () -> assertEquals(HOTEL_SEARCH, hotelSearchCount.search()),
                () -> assertEquals(COUNT, hotelSearchCount.count())
        );
    }


    //Validaciones para campos

    @Test
    @DisplayName("Debe lanzar excepción con el mensaje correcto cuando searchId es nulo")
    void should_throw_exception_when_searchId_is_null(){
        assertThatThrownBy(() -> new HotelSearchCount(null, HOTEL_SEARCH, COUNT))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'searchId'"));
    }

    @Test
    @DisplayName("Debe lanzar excepción con el mensaje correcto cuando search es nulo")
    void should_throw_exception_when_search_is_null(){
        assertThatThrownBy(() -> new HotelSearchCount(SEARCH_ID, null, COUNT))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'search'"));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, -100, -500})
    @DisplayName("Debe lanzar excepción con el mensaje correcto cuando count es negativo")
    void should_throw_exception_when_count_is_negative(long count){
        assertThatThrownBy(() -> new HotelSearchCount(SEARCH_ID, HOTEL_SEARCH, count))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.NEGATIVE_FIELD_MESSAGE_ERROR.formatted("'count'"));
    }

}