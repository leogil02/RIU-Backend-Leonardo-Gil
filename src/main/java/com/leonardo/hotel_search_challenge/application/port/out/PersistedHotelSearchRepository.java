package com.leonardo.hotel_search_challenge.application.port.out;

import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import com.leonardo.hotel_search_challenge.domain.model.PersistedHotelSearch;

import java.util.Optional;
import java.util.UUID;

public interface PersistedHotelSearchRepository {
    void save(PersistedHotelSearch search);
    long countMatching(HotelSearch hotelSearch);
    Optional<PersistedHotelSearch> findById(UUID searchId);
}
