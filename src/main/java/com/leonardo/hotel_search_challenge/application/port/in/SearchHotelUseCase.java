package com.leonardo.hotel_search_challenge.application.port.in;

import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;

import java.util.UUID;

public interface SearchHotelUseCase {
    UUID searchHotel(HotelSearch hotelSearch);
}
