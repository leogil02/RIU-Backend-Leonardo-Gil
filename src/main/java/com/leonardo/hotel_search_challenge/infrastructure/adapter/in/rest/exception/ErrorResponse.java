package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(

        @Schema(example = "2026-05-15T14:32:18.523Z")
        Instant timestamp,
        @Schema(example = "400")
        int status,
        @Schema(example = "BAD_REQUEST")
        String error,
        @Schema(example = "Validación fallida")
        String message,
        List<FieldError> fieldErrors
) {
    public record FieldError(
            @Schema(example = "hotelId")
            String field,
            @Schema(example = "El campo 'hotelId' no puede estar vacío")
            String message
    ){}

    public static ErrorResponse of(HttpStatus status, String message){
        return new ErrorResponse(Instant.now(), status.value(), status.name(), message, null);
    }

    public static ErrorResponse of(HttpStatus status, String message, List<FieldError> fieldErrors){
        return new ErrorResponse(Instant.now(), status.value(), status.name(), message, fieldErrors);
    }

}
