package com.leonardo.hotel_search_challenge.domain.model;

import com.leonardo.hotel_search_challenge.domain.exception.DomainValidationException;
import com.leonardo.hotel_search_challenge.domain.shared.GlobalMessages;

import java.util.UUID;

public record HotelSearchCount(
        UUID searchId,
        HotelSearch search,
        long count
) {

    public HotelSearchCount{
        //Validaciones de nulos
        if(searchId == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'searchId'"));
        if(search == null) throw new DomainValidationException(GlobalMessages.NULL_FIELD_MESSAGE_ERROR.formatted("'search'"));
        //Validación "count" no puede ser negativo
        if (count < 0) throw new DomainValidationException(GlobalMessages.NEGATIVE_FIELD_MESSAGE_ERROR.formatted("'count'"));
    }

}