package com.leonardo.hotel_search_challenge.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record HotelSearch(
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages
) {

    public HotelSearch {
        //Validaciones para "hotelId"
        Objects.requireNonNull(hotelId, "El campo 'hotelId' no puede ser nulo");
        if(hotelId.isBlank()) throw new IllegalArgumentException("El campo 'hotelId' no puede estar vacío");

        //Validaciones para "checkIn" y "checkOut"
        Objects.requireNonNull(checkIn, "El campo 'checkIn' no puede ser nulo");
        Objects.requireNonNull(checkOut, "El campo 'checkOut' no puede ser nulo");
        if(!checkIn.isBefore(checkOut)) throw new IllegalArgumentException("El checkIn debe ser anterior al checkOut");

        //Validaciones para "ages"
        Objects.requireNonNull(ages, "El campo 'ages' no puede ser nulo");
        if(ages.isEmpty()) throw new IllegalArgumentException("El campo 'ages' no puede estar vacío");
        for(Integer age : ages){
            if(age < 0) throw new IllegalArgumentException("El campo 'age' debe ser mayor o igual a 0");
        }

        //Copia defensiva de "ages"
        ages = List.copyOf(ages);

    }

}