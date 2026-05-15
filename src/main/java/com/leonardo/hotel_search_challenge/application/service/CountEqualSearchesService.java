package com.leonardo.hotel_search_challenge.application.service;

import com.leonardo.hotel_search_challenge.application.port.in.CountEqualSearchesUseCase;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearchCount;

import java.util.UUID;

public class CountEqualSearchesService implements CountEqualSearchesUseCase {
    @Override
    public HotelSearchCount count(UUID searchId) {
        return null;
    }
}
