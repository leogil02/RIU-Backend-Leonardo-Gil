package com.leonardo.hotel_search_challenge.infrastructure.config;

import com.leonardo.hotel_search_challenge.application.port.in.CountEqualSearchesUseCase;
import com.leonardo.hotel_search_challenge.application.port.in.SaveSearchUseCase;
import com.leonardo.hotel_search_challenge.application.port.in.SearchHotelUseCase;
import com.leonardo.hotel_search_challenge.application.port.out.HotelSearchedEventPublisher;
import com.leonardo.hotel_search_challenge.application.port.out.PersistedHotelSearchRepository;
import com.leonardo.hotel_search_challenge.application.service.CountEqualSearchesService;
import com.leonardo.hotel_search_challenge.application.service.SaveSearchService;
import com.leonardo.hotel_search_challenge.application.service.SearchHotelService;
import com.leonardo.hotel_search_challenge.infrastructure.transaction.TransactionalCountEqualSearchesService;
import com.leonardo.hotel_search_challenge.infrastructure.transaction.TransactionalSaveSearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesBeanConfiguration {

    @Bean
    public SearchHotelUseCase searchHotelUseCase(
            HotelSearchedEventPublisher hotelSearchedEventPublisher
    ){
        return new SearchHotelService(hotelSearchedEventPublisher);
    }

    @Bean
    public SaveSearchUseCase saveSearchUseCase(
            PersistedHotelSearchRepository persistedHotelSearchRepository
    ){
        return new TransactionalSaveSearchService(
                new SaveSearchService(persistedHotelSearchRepository)
        );
    }

    @Bean
    public CountEqualSearchesUseCase countEqualSearchesUseCase(
            PersistedHotelSearchRepository persistedHotelSearchRepository
    ){
        return new TransactionalCountEqualSearchesService(
                new CountEqualSearchesService(persistedHotelSearchRepository)
        );
    }

}
