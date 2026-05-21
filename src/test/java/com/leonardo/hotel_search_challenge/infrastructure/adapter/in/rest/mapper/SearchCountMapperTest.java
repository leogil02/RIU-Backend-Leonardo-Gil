package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.mapper;

import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchCountResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.leonardo.hotel_search_challenge.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class SearchCountMapperTest {

    @Test
    @DisplayName("Debe mapear los campos correctos cuando se ejecuta el método toResponse de HotelSearchCount a SearchCountResponse")
    void should_map_correct_fields_from_hotelSearchCount_to_searchCountResponse(){
        SearchCountResponse searchCountResponse = SearchCountMapper.toResponse(HOTEL_SEARCH_COUNT);
        assertAll(
                () -> assertEquals(SEARCH_ID, searchCountResponse.searchId()),
                () -> assertEquals(HOTEL_ID, searchCountResponse.search().hotelId()),
                () -> assertEquals(CHECK_IN, searchCountResponse.search().checkIn()),
                () -> assertEquals(CHECK_OUT, searchCountResponse.search().checkOut()),
                () -> assertEquals(AGES, searchCountResponse.search().ages()),
                () -> assertEquals(COUNT, searchCountResponse.count())
        );
    }

}