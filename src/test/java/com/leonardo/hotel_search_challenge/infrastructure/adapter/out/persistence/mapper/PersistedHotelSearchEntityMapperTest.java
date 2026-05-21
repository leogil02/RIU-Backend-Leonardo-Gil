package com.leonardo.hotel_search_challenge.infrastructure.adapter.out.persistence.mapper;

import com.leonardo.hotel_search_challenge.domain.model.PersistedHotelSearch;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.out.persistence.PersistedHotelSearchEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.leonardo.hotel_search_challenge.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class PersistedHotelSearchEntityMapperTest {

    @Test
    @DisplayName("Debe mapear los campos correctos cuando se ejecuta toEntity para pasar de " +
            "PersistedHotelSearch a PersistedHotelSearchEntity")
    void should_map_correct_fields_from_persistedHotelSearch_to_persistedHotelSearchEntity(){
        PersistedHotelSearchEntity result = PersistedHotelSearchEntityMapper.toEntity(PERSISTED_HOTEL_SEARCH);
        assertAll(
                () -> assertEquals(SEARCH_ID, result.getSearchId()),
                () -> assertEquals(HOTEL_ID, result.getHotelId()),
                () -> assertEquals(CHECK_IN, result.getCheckIn()),
                () -> assertEquals(CHECK_OUT, result.getCheckOut()),
                () -> assertEquals(AGES, result.getAges()),
                () -> assertEquals(OCCURRED_AT, result.getOccurredAt())
        );
    }

    @Test
    @DisplayName("Debe mapear los campos correctos cuando se ejecuta toDomain para pasar de " +
            "PersistedHotelSearchEntity a PersistedHotelSearch")
    void should_map_correct_fields_from_persistedHotelSearchEntity_to_persistedHotelSearch(){
        PersistedHotelSearch result = PersistedHotelSearchEntityMapper.toDomain(PERSISTED_HOTEL_SEARCH_ENTITY);
        assertAll(
                () -> assertEquals(SEARCH_ID, result.searchId()),
                () -> assertEquals(HOTEL_ID, result.hotelSearch().hotelId()),
                () -> assertEquals(CHECK_IN, result.hotelSearch().checkIn()),
                () -> assertEquals(CHECK_OUT, result.hotelSearch().checkOut()),
                () -> assertEquals(AGES, result.hotelSearch().ages()),
                () -> assertEquals(OCCURRED_AT, result.occurredAt())
        );
    }

}