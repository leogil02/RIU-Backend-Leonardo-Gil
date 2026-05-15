package com.leonardo.hotel_search_challenge.application.service;

import com.leonardo.hotel_search_challenge.application.port.in.SaveSearchUseCase;
import com.leonardo.hotel_search_challenge.application.port.out.HotelSearchedEventRepository;
import com.leonardo.hotel_search_challenge.domain.event.HotelSearchedEvent;
import org.springframework.stereotype.Service;

@Service
public class SaveSearchService implements SaveSearchUseCase {

    private final HotelSearchedEventRepository hotelSearchedEventRepository;

    public SaveSearchService(HotelSearchedEventRepository hotelSearchedEventRepository) {
        this.hotelSearchedEventRepository = hotelSearchedEventRepository;
    }

    @Override
    public void save(HotelSearchedEvent event) {
        hotelSearchedEventRepository.save(event);
    }
}
