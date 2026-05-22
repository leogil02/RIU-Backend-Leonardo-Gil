package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Respuesta de la consulta de coincidencias")
public record SearchCountResponse(
        @Schema(example = "123e4567-e89b-12d3-a456-426614174000")
        UUID searchId,
        SearchView search,
        @Schema(example = "100")
        Long count
) {
}
