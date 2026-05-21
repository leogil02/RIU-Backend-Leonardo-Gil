package com.leonardo.hotel_search_challenge.application.service;

import com.leonardo.hotel_search_challenge.application.port.in.CountEqualSearchesUseCase;
import com.leonardo.hotel_search_challenge.application.port.out.PersistedHotelSearchRepository;
import com.leonardo.hotel_search_challenge.domain.exception.SearchNotFoundException;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearchCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CountEqualSearchesService implements CountEqualSearchesUseCase {

    private final PersistedHotelSearchRepository persistedHotelSearchRepository;

    public CountEqualSearchesService(PersistedHotelSearchRepository persistedHotelSearchRepository) {
        this.persistedHotelSearchRepository = persistedHotelSearchRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public HotelSearchCount count(UUID searchId) {
        return persistedHotelSearchRepository
                .findById(searchId)
                .map(search -> {
                    HotelSearch hotelSearch = search.hotelSearch();
                    long total = persistedHotelSearchRepository.countMatching(hotelSearch);
                    return new HotelSearchCount(searchId, hotelSearch, total);
                })
                .orElseThrow(() -> new SearchNotFoundException(searchId));
    }

}
