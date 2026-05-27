package com.leonardo.hotel_search_challenge.domain.model;

import static com.leonardo.hotel_search_challenge.TestData.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import com.leonardo.hotel_search_challenge.domain.exception.DomainValidationException;
import com.leonardo.hotel_search_challenge.domain.shared.GlobalMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelSearchTest {

    //Construcción exitosa del objeto

    @Test
    @DisplayName("Debe construirse de forma correcta con datos válidos")
    void should_create_successfully_with_valid_data(){
        var search = new HotelSearch(HOTEL_ID, CHECK_IN, CHECK_OUT, AGES);
        assertAll(
                () -> assertEquals(HOTEL_ID, search.hotelId()),
                () -> assertEquals(CHECK_IN, search.checkIn()),
                () -> assertEquals(CHECK_OUT, search.checkOut()),
                () -> assertEquals(AGES, search.ages())
        );
    }

    @Test
    @DisplayName("Debe construirse correctamente con edades igualadas a 0")
    void should_create_successfully_with_age_zero(){
        var search = new HotelSearch(HOTEL_ID, CHECK_IN, CHECK_OUT, List.of(0,24,6));
        assertAll(
                () -> assertEquals(HOTEL_ID, search.hotelId()),
                () -> assertEquals(CHECK_IN, search.checkIn()),
                () -> assertEquals(CHECK_OUT, search.checkOut()),
                () -> assertEquals(List.of(0,24,6), search.ages())
        );
    }


    // Validaciones para hotelId

    @Test
    @DisplayName("Debe lanzar excepción con el mensaje correcto cuando hotelId es nulo")
    void should_throw_exception_when_hotelId_is_null(){
        assertThatThrownBy(() -> new HotelSearch(null, CHECK_IN, CHECK_OUT, AGES))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'hotelId'"));
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"  ", "\t","\n"})
    @DisplayName("Debe lanzar excepción con el mensaje correcto cuando hotelId es vacío")
    void should_throw_exception_when_hotelId_is_blank_or_empty(String hotelId){
        assertThatThrownBy(() -> new HotelSearch(hotelId, CHECK_IN, CHECK_OUT, AGES))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.BLANK_OR_EMPTY_FIELD_MESSAGE_ERROR.formatted("'hotelId'"));
    }


    //Validaciones para checkIn y checkOut

    @Test
    @DisplayName("Debe lanzar excepción cuando checkIn es nulo")
    void should_throw_exception_when_checkIn_is_null(){
        assertThatThrownBy(() -> new HotelSearch(HOTEL_ID, null, CHECK_OUT, AGES))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'checkIn'"));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando checkOut es nulo")
    void should_throw_exception_when_checkOut_is_null(){
        assertThatThrownBy(() -> new HotelSearch(HOTEL_ID, CHECK_IN, null, AGES))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'checkOut'"));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando checkIn es igual a checkOut")
    void should_throw_exception_when_checkIn_equals_checkOut(){
        assertThatThrownBy(() -> new HotelSearch(HOTEL_ID, CHECK_IN, CHECK_IN, AGES))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.CHECK_IN_BEFORE_CHECK_OUT_MESSAGE_ERROR);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando checkIn es posterior a checkOut")
    void should_throw_exception_when_checkIn_is_after_checkOut(){
        assertThatThrownBy(() -> new HotelSearch(HOTEL_ID, CHECK_OUT, CHECK_IN, AGES))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.CHECK_IN_BEFORE_CHECK_OUT_MESSAGE_ERROR);
    }


    // Validaciones para ages

    @Test
    @DisplayName("Debe lanzar excepción cuando ages sea nulo")
    void should_throw_exception_when_ages_is_null(){
        assertThatThrownBy(() -> new HotelSearch(HOTEL_ID, CHECK_IN, CHECK_OUT, null))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'ages'"));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando ages esté vacío")
    void should_throw_exception_when_ages_is_empty(){
        assertThatThrownBy(() -> new HotelSearch(HOTEL_ID, CHECK_IN, CHECK_OUT, List.of()))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.BLANK_OR_EMPTY_FIELD_MESSAGE_ERROR.formatted("'ages'"));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando algún elemento de ages sea menor a cero")
    void should_throw_exception_when_ages_have_negative_element(){
        assertThatThrownBy(() -> new HotelSearch(HOTEL_ID, CHECK_IN, CHECK_OUT, List.of(30, -1, -5)))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.NEGATIVE_FIELD_MESSAGE_ERROR.formatted("'age' del listado 'ages'"));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando algún elemento de ages sea nulo")
    void should_throw_exception_when_ages_have_null_element(){
        List<Integer> agesWithNull = new ArrayList<>();
        agesWithNull.add(30);
        agesWithNull.add(null);
        agesWithNull.add(5);

        assertThatThrownBy(() -> new HotelSearch(HOTEL_ID, CHECK_IN, CHECK_OUT, agesWithNull))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'age' del listado 'ages'"));
    }


    //Inmutabilidad para lista "ages"

    @Test
    @DisplayName("La lista 'ages' no debe poder modificarse de forma externa")
    void should_not_modify_ages_list_externally(){
        List<Integer> mutableAges = new ArrayList<>(List.of(30, 20, 0));
        var search = new HotelSearch(HOTEL_ID, CHECK_IN, CHECK_OUT, mutableAges);

        mutableAges.add(10);

        assertThat(search.ages()).doesNotContain(10);
    }

}