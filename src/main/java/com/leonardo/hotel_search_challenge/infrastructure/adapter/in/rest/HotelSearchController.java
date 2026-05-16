package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest;

import com.leonardo.hotel_search_challenge.application.port.in.CountEqualSearchesUseCase;
import com.leonardo.hotel_search_challenge.application.port.in.SearchHotelUseCase;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchCountResponse;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchRequest;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchResponse;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.mapper.SearchCountMapper;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.mapper.SearchMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelSearchController {

    private final SearchHotelUseCase searchHotelUseCase;
    private final CountEqualSearchesUseCase countEqualSearchesUseCase;

    public HotelSearchController(SearchHotelUseCase searchHotelUseCase, CountEqualSearchesUseCase countEqualSearchesUseCase) {
        this.searchHotelUseCase = searchHotelUseCase;
        this.countEqualSearchesUseCase = countEqualSearchesUseCase;
    }

    @PostMapping("/search")
    public SearchResponse searchHotel(@Valid @RequestBody SearchRequest searchRequest){
        UUID searchId = searchHotelUseCase.searchHotel(SearchMapper.toDomain(searchRequest));
        return SearchMapper.toResponse(searchId);
    }

    @GetMapping("/count")
    public SearchCountResponse searchHotelCount(@RequestParam UUID searchId){
        return SearchCountMapper.toResponse(countEqualSearchesUseCase.count(searchId));
    }

}
