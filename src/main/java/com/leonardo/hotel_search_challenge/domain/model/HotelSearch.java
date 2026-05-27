package com.leonardo.hotel_search_challenge.domain.model;

import com.leonardo.hotel_search_challenge.domain.exception.DomainValidationException;
import com.leonardo.hotel_search_challenge.domain.shared.GlobalMessages;

import java.time.LocalDate;
import java.util.List;

public record HotelSearch(
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages
) {

    public HotelSearch {
        //Validaciones para "hotelId"
        if(hotelId == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'hotelId'"));
        if(hotelId.isBlank()) throw new DomainValidationException(GlobalMessages.BLANK_OR_EMPTY_FIELD_MESSAGE_ERROR.formatted("'hotelId'"));

        //Validaciones para "checkIn" y "checkOut"
        if(checkIn == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'checkIn'"));
        if(checkOut == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'checkOut'"));
        if(!checkIn.isBefore(checkOut)) throw new DomainValidationException(GlobalMessages.CHECK_IN_BEFORE_CHECK_OUT_MESSAGE_ERROR);

        //Validaciones para "ages"
        if(ages == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'ages'"));
        if(ages.isEmpty()) throw new DomainValidationException(GlobalMessages.BLANK_OR_EMPTY_FIELD_MESSAGE_ERROR.formatted("'ages'"));
        for(Integer age : ages){
            if(age == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'age' del listado 'ages'"));
            if(age < 0) throw new DomainValidationException(GlobalMessages.NEGATIVE_FIELD_MESSAGE_ERROR.formatted("'age' del listado 'ages'"));
        }

        //Copia defensiva de "ages"
        ages = List.copyOf(ages);

    }

}