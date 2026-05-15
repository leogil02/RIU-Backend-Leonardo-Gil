package com.leonardo.hotel_search_challenge.domain.model;

import java.util.Objects;
import java.util.UUID;

public record HotelSearchCount(
        UUID searchId,
        HotelSearch search,
        long count
) {

    public HotelSearchCount{
        //Validaciones de nulos
        Objects.requireNonNull(searchId, "El campo 'searchId' no puede ser nulo");
        Objects.requireNonNull(search, "El campo 'search' no puede ser nulo");
        //Validación "count" no puede ser negativo
        if (count < 0) throw new IllegalArgumentException("El campo 'count' no puede ser menor a 0");
    }

}