package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.mapper;

import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchRequest;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchResponse;

import java.util.UUID;

public final class SearchMapper {

    private SearchMapper(){}

    public static HotelSearch toDomain(SearchRequest request){
        return new HotelSearch(
                request.hotelId(),
                request.checkIn(),
                request.checkOut(),
                request.ages()
        );
    }

    public static SearchResponse toResponse(UUID searchId){
        return new SearchResponse(searchId);
    }

}
