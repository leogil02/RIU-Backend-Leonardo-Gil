package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record SearchView(
        @Schema(example = "1234aBc")
        String hotelId,

        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(example = "29/12/2023")
        LocalDate checkIn,

        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(example = "31/12/2023")
        LocalDate checkOut,

        @Schema(example = "[3, 29, 30, 1]")
        List<Long> ages
) {
}
