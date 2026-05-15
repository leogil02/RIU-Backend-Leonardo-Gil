package com.leonardo.hotel_search_challenge.application.service;

import com.leonardo.hotel_search_challenge.application.port.in.SearchHotelUseCase;
import com.leonardo.hotel_search_challenge.application.port.out.HotelSearchedEventPublisher;
import com.leonardo.hotel_search_challenge.domain.event.HotelSearchedEvent;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SearchHotelService implements SearchHotelUseCase {

    private final HotelSearchedEventPublisher hotelSearchedEventPublisher;

    public SearchHotelService(HotelSearchedEventPublisher hotelSearchedEventPublisher) {
        this.hotelSearchedEventPublisher = hotelSearchedEventPublisher;
    }

    @Override
    public UUID searchHotel(HotelSearch hotelSearch) {
        HotelSearchedEvent event = HotelSearchedEvent.create(hotelSearch);
        hotelSearchedEventPublisher.publish(event);
        return event.searchId();
    }
}
