package com.leonardo.hotel_search_challenge.application.service;

import com.leonardo.hotel_search_challenge.application.port.in.SearchHotelUseCase;
import com.leonardo.hotel_search_challenge.application.port.out.HotelSearchedEventPublisher;
import com.leonardo.hotel_search_challenge.domain.event.HotelSearchedEvent;
import com.leonardo.hotel_search_challenge.domain.exception.DomainValidationException;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import com.leonardo.hotel_search_challenge.domain.shared.GlobalMessages;

import java.time.LocalDate;
import java.util.UUID;

public class SearchHotelService implements SearchHotelUseCase {

    private final HotelSearchedEventPublisher hotelSearchedEventPublisher;

    public SearchHotelService(HotelSearchedEventPublisher hotelSearchedEventPublisher) {
        this.hotelSearchedEventPublisher = hotelSearchedEventPublisher;
    }

    @Override
    public UUID searchHotel(HotelSearch hotelSearch) {
        if(hotelSearch.checkIn().isBefore(LocalDate.now())) throw new DomainValidationException(GlobalMessages.CHECK_IN_BEFORE_TODAY_ERROR);

        HotelSearchedEvent event = HotelSearchedEvent.create(hotelSearch);
        hotelSearchedEventPublisher.publish(event);
        return event.searchId();
    }
}
