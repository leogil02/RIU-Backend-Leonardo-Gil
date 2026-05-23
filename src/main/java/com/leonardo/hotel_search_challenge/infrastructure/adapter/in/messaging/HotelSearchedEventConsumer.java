package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.messaging;

import com.leonardo.hotel_search_challenge.application.port.in.SaveSearchUseCase;
import com.leonardo.hotel_search_challenge.domain.event.HotelSearchedEvent;
import com.leonardo.hotel_search_challenge.infrastructure.config.KafkaTopics;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.messaging.mapper.HotelSearchedEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Component
public class HotelSearchedEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(HotelSearchedEventConsumer.class);
    private final SaveSearchUseCase saveSearchUseCase;

    public HotelSearchedEventConsumer(SaveSearchUseCase saveSearchUseCase) {
        this.saveSearchUseCase = saveSearchUseCase;
    }

    @KafkaListener(
            topics = KafkaTopics.HOTEL_AVAILABILITY_SEARCHES,
            groupId = "${spring.kafka.consumer.group-id}"
    )
    @RetryableTopic(
            attempts = "5",
            backOff = @BackOff(delay = 1000, multiplier = 1.5),
            dltTopicSuffix = ".dlt"
    )
    public void handleHotelSearchedEvent(HotelSearchedEvent event){
        log.info("Evento recibido en el tópico {} con searchId={}",KafkaTopics.HOTEL_AVAILABILITY_SEARCHES, event.searchId());
        saveSearchUseCase.save(HotelSearchedEventMapper.toDomainEntity(event));
    }

    @DltHandler
    public void handleDlt(HotelSearchedEvent event){
        log.error("Evento enviado a DLT por alcanzar el numero máximo de reintentos: searchId = {}",event.searchId());
    }

}
