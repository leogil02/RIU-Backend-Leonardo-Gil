package com.leonardo.hotel_search_challenge.domain.event;

import com.leonardo.hotel_search_challenge.domain.shared.GlobalMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.leonardo.hotel_search_challenge.TestData.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HotelSearchedEventTest {

    //Construcción exitosa

    @Test
    @DisplayName("Debe construirse correctamente con datos válidos")
    void should_build_successfully_with_valid_data(){
        var event = new HotelSearchedEvent(SEARCH_ID, HOTEL_SEARCH, OCCURRED_AT);

        assertAll(
                () -> assertEquals(SEARCH_ID, event.searchId()),
                () -> assertEquals(HOTEL_SEARCH, event.hotelSearch()),
                () -> assertEquals(OCCURRED_AT, event.occurredAt())
        );
    }


    //Validación factoría
    @Test
    @DisplayName("La factoría create() debe generar el evento con un searchId y un occurredAt no nulos")
    void should_create_event_with_non_null_searchId_and_occurredAt(){
        var event = HotelSearchedEvent.create(HOTEL_SEARCH);

        assertAll(
                () -> assertNotNull(event.searchId()),
                () -> assertEquals(HOTEL_SEARCH, event.hotelSearch()),
                () -> assertNotNull(event.occurredAt())
        );
    }

    @Test
    @DisplayName("La factoría create() debe generar el evento con distintos searchId")
    void should_create_event_with_different_searchId(){
        var event1 = HotelSearchedEvent.create(HOTEL_SEARCH);
        var event2 = HotelSearchedEvent.create(HOTEL_SEARCH);

        assertThat(event1.searchId()).isNotEqualTo(event2.searchId());
    }

    //Validación de campos

    @Test
    @DisplayName("Debe lanzar excepción con el mensaje correcto cuando searchId es nulo")
    void should_throw_exception_when_searchId_is_null(){
        assertThatThrownBy(() -> new HotelSearchedEvent(null, HOTEL_SEARCH, OCCURRED_AT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'searchId'"));
    }

    @Test
    @DisplayName("Debe lanzar excepción con el mensaje correcto cuando hotelSearch es nulo")
    void should_throw_exception_when_hotelSearch_is_null(){
        assertThatThrownBy(() -> new HotelSearchedEvent(SEARCH_ID, null, OCCURRED_AT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'hotelSearch'"));
    }

    @Test
    @DisplayName("Debe lanzar excepción con el mensaje correcto cuando occurredAt es nulo")
    void should_throw_exception_when_occurredAt_is_null(){
        assertThatThrownBy(() -> new HotelSearchedEvent(SEARCH_ID, HOTEL_SEARCH, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'occurredAt'"));
    }



}