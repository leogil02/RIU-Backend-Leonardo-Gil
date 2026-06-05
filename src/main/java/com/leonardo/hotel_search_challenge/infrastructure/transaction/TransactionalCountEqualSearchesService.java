package com.leonardo.hotel_search_challenge.infrastructure.transaction;

import com.leonardo.hotel_search_challenge.application.port.in.CountEqualSearchesUseCase;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearchCount;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class TransactionalCountEqualSearchesService implements CountEqualSearchesUseCase {

    private final CountEqualSearchesUseCase delegate;

    public TransactionalCountEqualSearchesService(CountEqualSearchesUseCase delegate) {
        this.delegate = delegate;
    }

    @Transactional(readOnly = true)
    @Override
    public HotelSearchCount count(UUID searchId) {
        return delegate.count(searchId);
    }
}
