package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.mapper;

import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearchCount;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchCountResponse;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchView;

public final class SearchCountMapper {

    private SearchCountMapper(){}

    public static SearchCountResponse toResponse(HotelSearchCount hotelSearchCount){
        HotelSearch search = hotelSearchCount.search();
        SearchView view = new SearchView(
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                search.ages()
        );

        return new SearchCountResponse(
                hotelSearchCount.searchId(),
                view,
                hotelSearchCount.count()
        );
    }

}
