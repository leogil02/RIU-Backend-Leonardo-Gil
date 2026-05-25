package com.leonardo.hotel_search_challenge.infrastructure.adapter.out.persistence;

import com.leonardo.hotel_search_challenge.application.port.out.PersistedHotelSearchRepository;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import com.leonardo.hotel_search_challenge.domain.model.PersistedHotelSearch;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.out.persistence.mapper.PersistedHotelSearchEntityMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PersistedHotelSearchRepositoryAdapter implements PersistedHotelSearchRepository {

    private final SpringDataPersistedHotelSearchRepository repository;
    private final AgeConverter ageConverter;

    public PersistedHotelSearchRepositoryAdapter(SpringDataPersistedHotelSearchRepository repository, AgeConverter ageConverter) {
        this.repository = repository;
        this.ageConverter = ageConverter;
    }

    @Override
    public void save(PersistedHotelSearch search) {
        repository.save(PersistedHotelSearchEntityMapper.toEntity(search));
    }

    @Override
    public long countMatching(HotelSearch hotelSearch) {
        return repository.countMatching(
               hotelSearch.hotelId(),
               hotelSearch.checkIn(),
               hotelSearch.checkOut(),
                ageConverter.convertToDatabaseColumn(hotelSearch.ages())
        );
    }

    @Override
    public Optional<PersistedHotelSearch> findById(UUID searchId) {
        return repository
                .findById(searchId)
                .map(PersistedHotelSearchEntityMapper::toDomain);
    }

    @Override
    public boolean existsById(UUID searchId) {
        return repository.existsById(searchId);
    }
}
