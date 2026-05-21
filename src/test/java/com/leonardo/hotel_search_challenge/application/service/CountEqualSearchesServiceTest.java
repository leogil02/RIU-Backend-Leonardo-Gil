package com.leonardo.hotel_search_challenge.application.service;

import com.leonardo.hotel_search_challenge.application.port.out.PersistedHotelSearchRepository;
import com.leonardo.hotel_search_challenge.domain.exception.SearchNotFoundException;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearchCount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.leonardo.hotel_search_challenge.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CountEqualSearchesServiceTest {

    @Mock
    private PersistedHotelSearchRepository repository;

    @InjectMocks
    private CountEqualSearchesService service;

    @Test
    @DisplayName("Debe devolver HotelSearchCount con el conteo correcto cuando el searchId existe")
    void should_return_count_when_searchId_exists(){

        when(repository.findById(SEARCH_ID)).thenReturn(Optional.of(PERSISTED_HOTEL_SEARCH));
        when(repository.countMatching(HOTEL_SEARCH)).thenReturn(COUNT);

        HotelSearchCount hotelSearchCount = service.count(SEARCH_ID);

        assertAll(
                () -> assertNotNull(hotelSearchCount),
                () -> assertEquals(SEARCH_ID, hotelSearchCount.searchId()),
                () -> assertEquals(HOTEL_SEARCH, hotelSearchCount.search()),
                () -> assertEquals(COUNT, hotelSearchCount.count())
        );

        verify(repository).countMatching(HOTEL_SEARCH);

    }

    @Test
    @DisplayName("Debe devolver excepción cuando no exista el searchId")
    void should_throw_exception_when_searchId_not_exists(){
        when(repository.findById(SEARCH_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.count(SEARCH_ID))
                .isInstanceOf(SearchNotFoundException.class)
                .hasMessageContaining(SEARCH_ID.toString());

    }

}