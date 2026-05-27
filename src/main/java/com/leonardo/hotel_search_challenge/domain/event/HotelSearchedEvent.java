package com.leonardo.hotel_search_challenge.domain.event;

import com.leonardo.hotel_search_challenge.domain.exception.DomainValidationException;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import com.leonardo.hotel_search_challenge.domain.shared.GlobalMessages;

import java.time.Instant;
import java.util.UUID;

public record HotelSearchedEvent(
        UUID searchId,
        HotelSearch hotelSearch,
        Instant occurredAt
) {

    public HotelSearchedEvent{
        //Validaciones de nulos
        if(searchId == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'searchId'"));
        if(hotelSearch == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'hotelSearch'"));
        if(occurredAt == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'occurredAt'"));
    }

    public static HotelSearchedEvent create(HotelSearch hotelSearch){
        return new HotelSearchedEvent(UUID.randomUUID(), hotelSearch, Instant.now());
    }

}