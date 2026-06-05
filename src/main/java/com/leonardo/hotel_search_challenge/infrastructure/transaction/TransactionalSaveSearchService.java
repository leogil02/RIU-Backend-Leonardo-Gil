package com.leonardo.hotel_search_challenge.infrastructure.transaction;

import com.leonardo.hotel_search_challenge.application.port.in.SaveSearchUseCase;
import com.leonardo.hotel_search_challenge.domain.model.PersistedHotelSearch;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalSaveSearchService implements SaveSearchUseCase {

    private final SaveSearchUseCase delegate;

    public TransactionalSaveSearchService(SaveSearchUseCase delegate) {
        this.delegate = delegate;
    }

    @Transactional
    @Override
    public void save(PersistedHotelSearch search) {
        delegate.save(search);
    }
}
