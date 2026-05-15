package com.leonardo.hotel_search_challenge.domain.exception;

import java.util.UUID;

public class SearchNotFoundException extends RuntimeException {
    public SearchNotFoundException(UUID searchId) {
        super("La búsqueda con id = %s no existe".formatted(searchId));
    }
}
