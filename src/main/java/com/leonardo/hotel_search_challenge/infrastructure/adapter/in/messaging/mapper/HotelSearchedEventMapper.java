package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.messaging.mapper;

import com.leonardo.hotel_search_challenge.domain.event.HotelSearchedEvent;
import com.leonardo.hotel_search_challenge.domain.model.PersistedHotelSearch;

public final class HotelSearchedEventMapper {

    private HotelSearchedEventMapper(){}

    public static PersistedHotelSearch toDomainEntity(HotelSearchedEvent event){
        return new PersistedHotelSearch(
                event.searchId(),
                event.hotelSearch(),
                event.occurredAt()
        );
    }

}
