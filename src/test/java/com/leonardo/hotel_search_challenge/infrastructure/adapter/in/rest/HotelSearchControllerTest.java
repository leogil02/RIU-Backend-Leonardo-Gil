package com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest;

import com.leonardo.hotel_search_challenge.application.port.in.CountEqualSearchesUseCase;
import com.leonardo.hotel_search_challenge.application.port.in.SearchHotelUseCase;
import com.leonardo.hotel_search_challenge.domain.exception.SearchNotFoundException;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchRequest;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.exception.GlobalExceptionHandler;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static com.leonardo.hotel_search_challenge.TestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HotelSearchController.class)
@Import(GlobalExceptionHandler.class)
class HotelSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SearchHotelUseCase searchHotelUseCase;

    @MockitoBean
    private CountEqualSearchesUseCase countEqualSearchesUseCase;

    @Autowired
    private ObjectMapper objectMapper;


    // Tests para POST /search

    @Test
    @DisplayName("POST /search debe devolver status code 200 con el searchId cuando el body es válido")
    void should_return_200_with_searchId_when_request_is_valid() throws Exception{

        when(searchHotelUseCase.searchHotel(any())).thenReturn(SEARCH_ID);

        mockMvc.perform(post("/api/v1/hotels/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(SEARCH_REQUEST))
        )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.searchId").value(SEARCH_ID.toString())
                );

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"\n", "  ", "\t"})
    @DisplayName("POST /search debe devolver status code 400 cuando hotelId está vacío o es nulo")
    void should_return_400_when_hotelId_is_blank_or_null(String hotelId) throws Exception {
        SearchRequest searchRequest = new SearchRequest(hotelId, CHECK_IN, CHECK_OUT, AGES);

        mockMvc.perform(post("/api/v1/hotels/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest))
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.fieldErrors[0].field").value("hotelId")
                );
    }

    @Test
    @DisplayName("POST /search debe devolver status code 400 cuando checkIn es nulo")
    void should_return_400_when_checkIn_is_null() throws Exception {
        SearchRequest searchRequest = new SearchRequest(HOTEL_ID, null, CHECK_OUT, AGES);

        mockMvc.perform(post("/api/v1/hotels/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest))
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.fieldErrors[0].field").value("checkIn")
                );
    }

    @Test
    @DisplayName("POST /search debe devolver status code 400 cuando checkOut es nulo")
    void should_return_400_when_checkOut_is_null() throws Exception {
        SearchRequest searchRequest = new SearchRequest(HOTEL_ID, CHECK_IN, null, AGES);

        mockMvc.perform(post("/api/v1/hotels/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest))
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.fieldErrors[0].field").value("checkOut")
                );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("POST /search debe devolver status code 400 cuando ages está vacío o es nulo")
    void should_return_400_when_ages_is_blank_or_null(List<Integer> ages) throws Exception {
        SearchRequest searchRequest = new SearchRequest(HOTEL_ID, CHECK_IN, CHECK_OUT, ages);

        mockMvc.perform(post("/api/v1/hotels/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest))
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.fieldErrors[0].field").value("ages")
                );
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-4, -10})
    @DisplayName("POST /search debe devolver status code 400 cuando ages tiene un elemento negativo o nulo")
    void should_return_400_when_ages_have_a_negative_or_null_element(Integer wrongAge) throws Exception {
        List<Integer> ages = new ArrayList<>();
        ages.add(30);
        ages.add(wrongAge);
        ages.add(19);

        SearchRequest searchRequest = new SearchRequest(HOTEL_ID, CHECK_IN, CHECK_OUT, ages);

        mockMvc.perform(post("/api/v1/hotels/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest))
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.fieldErrors[0].field").value(Matchers.startsWith("ages"))
                );

    }

    @Test
    @DisplayName("POST /search debe devolver status code 400 cuando el body es un JDON mal formado")
    void should_return_400_when_body_is_malformed_json() throws Exception {

        mockMvc.perform(post("/api/v1/hotels/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{json mal formado}")
                )
                .andExpectAll(
                        status().isBadRequest()
                );
    }


    // Tests para GET /count

    @Test
    @DisplayName("GET /count debe devolver status code 200 con un SearchCountResponse cuando el searchId existe")
    void should_return_200_and_searchCountResponse_when_searchId_exists() throws Exception {
        when(countEqualSearchesUseCase.count(SEARCH_ID)).thenReturn(HOTEL_SEARCH_COUNT);

        mockMvc.perform(get("/api/v1/hotels/count")
                .param("searchId", SEARCH_ID.toString())
        )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.searchId").value(SEARCH_ID.toString()),
                        jsonPath("$.search.hotelId").value(HOTEL_SEARCH_COUNT.search().hotelId()),
                        jsonPath("$.count").value(HOTEL_SEARCH_COUNT.count())
                );
    }

    @Test
    @DisplayName("GET /count debe devolver status code 404 cuando el searchId no existe")
    void should_return_404_when_searchId_does_not_exist() throws Exception {
        when(countEqualSearchesUseCase.count(SEARCH_ID)).thenThrow(new SearchNotFoundException(SEARCH_ID));

        mockMvc.perform(get("/api/v1/hotels/count")
                        .param("searchId", SEARCH_ID.toString())
                )
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(Matchers.containsString(SEARCH_ID.toString()))
                );
    }

}