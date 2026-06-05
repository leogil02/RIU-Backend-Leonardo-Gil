package com.leonardo.hotel_search_challenge.application.service;

import com.leonardo.hotel_search_challenge.application.port.in.SaveSearchUseCase;
import com.leonardo.hotel_search_challenge.application.port.out.PersistedHotelSearchRepository;
import com.leonardo.hotel_search_challenge.domain.model.PersistedHotelSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveSearchService implements SaveSearchUseCase {

    private static final Logger log = LoggerFactory.getLogger(SaveSearchService.class);
    private final PersistedHotelSearchRepository persistedHotelSearchRepository;

    public SaveSearchService(PersistedHotelSearchRepository persistedHotelSearchRepository) {
        this.persistedHotelSearchRepository = persistedHotelSearchRepository;
    }

    @Override
    public void save(PersistedHotelSearch search) {
        if(persistedHotelSearchRepository.existsById(search.searchId())){
            log.info("Evento procesado: la búsqueda ya está persistida. Se ignora evento con searchId={}", search.searchId());
            return;
        }
        persistedHotelSearchRepository.save(search);
    }
}
