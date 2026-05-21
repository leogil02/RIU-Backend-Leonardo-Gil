package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.messaging.mapper;

import com.leonardo.hotel_search_challenge.domain.model.PersistedHotelSearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.leonardo.hotel_search_challenge.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class HotelSearchedEventMapperTest {

    @Test
    @DisplayName("Debe mapear los campos correctos cuando se ejecuta toDomainEntity para pasar de HotelSearchedEvent a PersistedHotelSearch")
    void should_map_correct_fields_from_searchRequest_to_hotelSearch(){
        PersistedHotelSearch result = HotelSearchedEventMapper.toDomainEntity(HOTEL_SEARCHED_EVENT);

        assertAll(
                () -> assertEquals(SEARCH_ID, result.searchId()),
                () -> assertEquals(HOTEL_SEARCH, result.hotelSearch()),
                () -> assertEquals(OCCURRED_AT, result.occurredAt())
        );

    }

}