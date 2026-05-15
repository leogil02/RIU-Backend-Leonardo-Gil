package com.leonardo.hotel_search_challenge.domain.event;

import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record HotelSearchedEvent(
        UUID searchId,
        HotelSearch hotelSearch,
        Instant timestamp
) {

    public HotelSearchedEvent{
        //Validaciones de nulos
        Objects.requireNonNull(searchId, "El campo 'searchId' no puede ser nulo");
        Objects.requireNonNull(hotelSearch, "El campo 'hotelSearch' no puede ser nulo");
        Objects.requireNonNull(timestamp, "El campo 'timestamp' no puede ser nulo");
    }

    public static HotelSearchedEvent create(HotelSearch hotelSearch){
        return new HotelSearchedEvent(UUID.randomUUID(), hotelSearch, Instant.now());
    }

}