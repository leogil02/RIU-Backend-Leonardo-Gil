package com.leonardo.hotel_search_challenge.infrastructure.adapter.out.persistence.mapper;

import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import com.leonardo.hotel_search_challenge.domain.model.PersistedHotelSearch;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.out.persistence.PersistedHotelSearchEntity;

public final class PersistedHotelSearchEntityMapper {

    private PersistedHotelSearchEntityMapper(){}

    public static PersistedHotelSearchEntity toEntity(PersistedHotelSearch search){
        HotelSearch hotelSearch = search.hotelSearch();
        return new PersistedHotelSearchEntity(
                search.searchId(),
                hotelSearch.hotelId(),
                hotelSearch.checkIn(),
                hotelSearch.checkOut(),
                hotelSearch.ages(),
                search.occurredAt()
        );
    }

    public static PersistedHotelSearch toDomain(PersistedHotelSearchEntity entity){
        HotelSearch hotelSearch = new HotelSearch(
                entity.getHotelId(),
                entity.getCheckIn(),
                entity.getCheckOut(),
                entity.getAges()
        );
        return new PersistedHotelSearch(
                entity.getSearchId(),
                hotelSearch,
                entity.getOccurredAt()
        );
    }

}
