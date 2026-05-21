package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.mapper;

import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.leonardo.hotel_search_challenge.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class SearchMapperTest {

    @Test
    @DisplayName("Debe mapear los campos correctos cuando se ejecuta toDomain para pasar de SearchRequest a HotelSearch")
    void should_map_correct_fields_from_searchRequest_to_hotelSearch(){
        HotelSearch hotelSearch = SearchMapper.toDomain(SEARCH_REQUEST);
        assertAll(
                () -> assertEquals(HOTEL_ID, hotelSearch.hotelId()),
                () -> assertEquals(CHECK_IN, hotelSearch.checkIn()),
                () -> assertEquals(CHECK_OUT, hotelSearch.checkOut()),
                () -> assertEquals(AGES, hotelSearch.ages())
        );
    }

    @Test
    @DisplayName("Debe mapear los campos correctos cuando se ejecuta toResponse para convertir un UUID en SearchResponse")
    void should_map_correct_fields_from_uuid_to_searchResponse(){
        SearchResponse searchResponse = SearchMapper.toResponse(SEARCH_ID);
        assertEquals(SEARCH_ID, searchResponse.searchId());
    }

}