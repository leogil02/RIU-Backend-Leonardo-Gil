package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Criterios de búsqueda de un hotel")
public record SearchRequest(
        @NotBlank(message = "El campo 'hotelId' no puede estar vacío")
        @Schema(example = "1234aBc")
        String hotelId,

        @NotNull(message = "El campo 'checkIn' no puede estar vacío")
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(example = "29/12/2023")
        LocalDate checkIn,

        @NotNull(message = "El campo 'checkOut' no puede estar vacío")
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(example = "31/12/2023")
        LocalDate checkOut,

        @NotEmpty(message = "El campo 'ages' no puede estar vacío")
        @Schema(example = "[30, 29, 1, 3]")
        List<
                @NotNull(message = "Las edades dentro del campo 'ages' no pueden ser nulas")
                @PositiveOrZero(message = "Las edades dentro del campo 'ages' deben ser mayores o iguales a 0")
                Integer
        > ages
) {
}
