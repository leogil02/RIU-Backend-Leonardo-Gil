package com.leonardo.hotel_search_challenge.application.port.in;

import com.leonardo.hotel_search_challenge.domain.model.PersistedHotelSearch;

public interface SaveSearchUseCase {
    void save(PersistedHotelSearch search);
}
