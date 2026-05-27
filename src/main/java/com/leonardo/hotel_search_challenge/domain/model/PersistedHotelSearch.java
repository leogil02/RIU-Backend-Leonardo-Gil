package com.leonardo.hotel_search_challenge.domain.model;

import com.leonardo.hotel_search_challenge.domain.exception.DomainValidationException;
import com.leonardo.hotel_search_challenge.domain.shared.GlobalMessages;

import java.time.Instant;
import java.util.UUID;

public record PersistedHotelSearch(
        UUID searchId,
        HotelSearch hotelSearch,
        Instant occurredAt
) {

    public PersistedHotelSearch{
        if(searchId == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'searchId'"));
        if(hotelSearch == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'hotelSearch'"));
        if(occurredAt == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'occurredAt'"));
    }

}
