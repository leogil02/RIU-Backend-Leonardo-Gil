package com.leonardo.hotel_search_challenge.application.service;

import com.leonardo.hotel_search_challenge.application.port.in.CountEqualSearchesUseCase;
import com.leonardo.hotel_search_challenge.application.port.out.HotelSearchedEventRepository;
import com.leonardo.hotel_search_challenge.domain.exception.SearchNotFoundException;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearchCount;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CountEqualSearchesService implements CountEqualSearchesUseCase {

    private final HotelSearchedEventRepository hotelSearchedEventRepository;

    public CountEqualSearchesService(HotelSearchedEventRepository hotelSearchedEventRepository) {
        this.hotelSearchedEventRepository = hotelSearchedEventRepository;
    }

    @Override
    public HotelSearchCount count(UUID searchId) {
        return hotelSearchedEventRepository
                .findById(searchId)
                .map(event -> {
                    HotelSearch hotelSearch = event.hotelSearch();
                    long total = hotelSearchedEventRepository.countMatching(hotelSearch);
                    return new HotelSearchCount(searchId, hotelSearch, total);
                })
                .orElseThrow(() -> new SearchNotFoundException(searchId));
    }

}
