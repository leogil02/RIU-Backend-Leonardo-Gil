package com.leonardo.hotel_search_challenge.application.service;

import com.leonardo.hotel_search_challenge.application.port.out.HotelSearchedEventPublisher;
import com.leonardo.hotel_search_challenge.domain.event.HotelSearchedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.leonardo.hotel_search_challenge.TestData.HOTEL_SEARCH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SearchHotelServiceTest {

    @Mock
    private HotelSearchedEventPublisher publisher;

    @InjectMocks
    private SearchHotelService service;

    @Test
    @DisplayName("Debe publicar el evento y devolver el searchId generado")
    void should_publish_event_and_return_generated_searchId(){

        UUID result = service.searchHotel(HOTEL_SEARCH);

        ArgumentCaptor<HotelSearchedEvent> captor = ArgumentCaptor.forClass(HotelSearchedEvent.class);

        verify(publisher).publish(captor.capture());

        HotelSearchedEvent event = captor.getValue();

        assertAll(
                () -> assertNotNull(result),
                () -> assertNotNull(event.searchId()),
                () -> assertNotNull(event.occurredAt()),
                () -> assertThat(event.searchId()).isEqualTo(result),
                () -> assertThat(event.hotelSearch()).isEqualTo(HOTEL_SEARCH)
        );

    }

}