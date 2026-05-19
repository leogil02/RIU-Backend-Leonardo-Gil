package com.leonardo.hotel_search_challenge.infrastructure.adapter.out.messaging;

import com.leonardo.hotel_search_challenge.application.port.out.HotelSearchedEventPublisher;
import com.leonardo.hotel_search_challenge.domain.event.HotelSearchedEvent;
import com.leonardo.hotel_search_challenge.infrastructure.config.KafkaTopics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaHotelSearchedEventPublisher implements HotelSearchedEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaHotelSearchedEventPublisher.class);

    private final KafkaTemplate<String, HotelSearchedEvent> kafkaTemplate;

    public KafkaHotelSearchedEventPublisher(KafkaTemplate<String, HotelSearchedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(HotelSearchedEvent event) {
        kafkaTemplate.send(KafkaTopics.HOTEL_AVAILABILITY_SEARCHES, event.searchId().toString(), event)
                .whenComplete((result, ex) -> {
                    if(ex != null){
                        log.error("Error al publicar evento con searchId = {} en el tópico {}: {}",
                                event.searchId(),
                                KafkaTopics.HOTEL_AVAILABILITY_SEARCHES,
                                ex.getMessage()
                        );
                    } else {
                        log.info("Evento publicado correctamente al tópico {}: searchId={}, partición={}, offset={}",
                                KafkaTopics.HOTEL_AVAILABILITY_SEARCHES,
                                event.searchId(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset()
                        );
                    }
                });
    }

}
