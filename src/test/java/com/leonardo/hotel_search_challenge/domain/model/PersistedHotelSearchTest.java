package com.leonardo.hotel_search_challenge.domain.model;

import com.leonardo.hotel_search_challenge.domain.shared.GlobalMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static com.leonardo.hotel_search_challenge.TestData.*;


class PersistedHotelSearchTest {

    //Construcción exitosa

    @Test
    @DisplayName("Debe construirse correctamente con datos válidos")
    void should_build_successfully_with_valid_data(){
        var persistedHotelSearch = new PersistedHotelSearch(SEARCH_ID, HOTEL_SEARCH, OCCURRED_AT);

        assertAll(
                () -> assertEquals(SEARCH_ID, persistedHotelSearch.searchId()),
                () -> assertEquals(HOTEL_SEARCH, persistedHotelSearch.hotelSearch()),
                () -> assertEquals(OCCURRED_AT, persistedHotelSearch.occurredAt())
        );
    }


    //Validación de campos

    @Test
    @DisplayName("Debe lanzar excepción con el mensaje correcto cuando searchId es nulo")
    void should_throw_exception_when_searchId_is_null(){
        assertThatThrownBy(() -> new PersistedHotelSearch(null, HOTEL_SEARCH, OCCURRED_AT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'searchId'"));
    }

    @Test
    @DisplayName("Debe lanzar excepción con el mensaje correcto cuando hotelSearch es nulo")
    void should_throw_exception_when_hotelSearch_is_null(){
        assertThatThrownBy(() -> new PersistedHotelSearch(SEARCH_ID, null, OCCURRED_AT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'hotelSearch'"));
    }

    @Test
    @DisplayName("Debe lanzar excepción con el mensaje correcto cuando occurredAt es nulo")
    void should_throw_exception_when_occurredAt_is_null(){
        assertThatThrownBy(() -> new PersistedHotelSearch(SEARCH_ID, HOTEL_SEARCH, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'occurredAt'"));
    }

}