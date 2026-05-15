package com.leonardo.hotel_search_challenge.application.port.in;

import com.leonardo.hotel_search_challenge.domain.model.HotelSearchCount;

import java.util.UUID;

public interface CountEqualSearchesUseCase {
    HotelSearchCount count(UUID searchId);
}
