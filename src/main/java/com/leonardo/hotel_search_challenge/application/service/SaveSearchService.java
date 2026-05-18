package com.leonardo.hotel_search_challenge.application.service;

import com.leonardo.hotel_search_challenge.application.port.in.SaveSearchUseCase;
import com.leonardo.hotel_search_challenge.application.port.out.PersistedHotelSearchRepository;
import com.leonardo.hotel_search_challenge.domain.model.PersistedHotelSearch;
import org.springframework.stereotype.Service;

@Service
public class SaveSearchService implements SaveSearchUseCase {

    private final PersistedHotelSearchRepository persistedHotelSearchRepository;

    public SaveSearchService(PersistedHotelSearchRepository persistedHotelSearchRepository) {
        this.persistedHotelSearchRepository = persistedHotelSearchRepository;
    }

    @Override
    public void save(PersistedHotelSearch search) {
        persistedHotelSearchRepository.save(search);
    }
}
