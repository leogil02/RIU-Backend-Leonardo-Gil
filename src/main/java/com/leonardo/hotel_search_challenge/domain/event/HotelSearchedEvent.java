package com.leonardo.hotel_search_challenge.domain.event;

import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import com.leonardo.hotel_search_challenge.domain.shared.GlobalMessages;

import java.time.Instant;
import java.util.UUID;

public record HotelSearchedEvent(
        UUID searchId,
        HotelSearch hotelSearch,
        Instant timestamp
) {

    public HotelSearchedEvent{
        //Validaciones de nulos
        if(searchId == null) throw new IllegalArgumentException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'searchId'"));
        if(hotelSearch == null) throw new IllegalArgumentException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'hotelSearch'"));
        if(timestamp == null) throw new IllegalArgumentException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'timestamp'"));
    }

    public static HotelSearchedEvent create(HotelSearch hotelSearch){
        return new HotelSearchedEvent(UUID.randomUUID(), hotelSearch, Instant.now());
    }

}