package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest;

import com.leonardo.hotel_search_challenge.application.port.in.CountEqualSearchesUseCase;
import com.leonardo.hotel_search_challenge.application.port.in.SearchHotelUseCase;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchCountResponse;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchRequest;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchResponse;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.mapper.SearchCountMapper;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.mapper.SearchMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hotels")
@Tag(
        name = "Hotel Searches",
        description = "Operaciones para búsqueda de hoteles y consulta de coincidencias"
)
public class HotelSearchController {

    private final SearchHotelUseCase searchHotelUseCase;
    private final CountEqualSearchesUseCase countEqualSearchesUseCase;

    public HotelSearchController(SearchHotelUseCase searchHotelUseCase, CountEqualSearchesUseCase countEqualSearchesUseCase) {
        this.searchHotelUseCase = searchHotelUseCase;
        this.countEqualSearchesUseCase = countEqualSearchesUseCase;
    }

    @PostMapping("/search")
    @Operation(
            summary = "Registrar una nueva búsqueda",
            description = "Recibe los criterios de búsqueda para un hotel, publica un evento a Kafka y devuelve un " +
                    "ID único de la búsqueda.El evento se consume asincrónicamente y se persiste la búsqueda en la " +
                    "base de datos "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Búsqueda registrada exitosamente",
                    content = @Content(schema = @Schema(implementation = SearchResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Body inválido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public SearchResponse searchHotel(@Valid @RequestBody SearchRequest searchRequest){
        UUID searchId = searchHotelUseCase.searchHotel(SearchMapper.toDomain(searchRequest));
        return SearchMapper.toResponse(searchId);
    }

    @GetMapping("/count")
    @Operation(
            summary = "Contar búsquedas que coincidan exactamente con una búsqueda realizada",
            description = "Se envía por parámetro un searchId y devuelve los criterios de búsqueda de hotel y la " +
                    "cantidad totalde búsquedas con esos mismos criterios (hotelId, checkIn, checkOut y ages en " +
                    "el orden exacto que se buscó) "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Conteo obtenido exitosamente",
                    content = @Content(schema = @Schema(implementation = SearchCountResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Body inválido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe ninguna búsqueda con el searchId dado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public SearchCountResponse searchHotelCount(
            @Parameter(
                    description = "UUID de la búsqueda que se quiere usar para buscar coincidencias",
                    example = "123e4567-e89b-12d3-a456-426614174000",
                    required = true
            )
            @RequestParam UUID searchId
    ){
        return SearchCountMapper.toResponse(countEqualSearchesUseCase.count(searchId));
    }

}
