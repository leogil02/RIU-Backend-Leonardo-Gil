package com.leonardo.hotel_search_challenge.application.service;

import com.leonardo.hotel_search_challenge.application.port.out.PersistedHotelSearchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.leonardo.hotel_search_challenge.TestData.PERSISTED_HOTEL_SEARCH;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveSearchServiceTest {

    @Mock
    private PersistedHotelSearchRepository repository;

    @InjectMocks
    private SaveSearchService service;

    @Test
    @DisplayName("Debe llamar al repositorio para guardar la búsqueda")
    void should_call_repository_to_save_search(){
        service.save(PERSISTED_HOTEL_SEARCH);
        verify(repository).save(PERSISTED_HOTEL_SEARCH);
    }

}