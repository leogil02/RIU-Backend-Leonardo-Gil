package com.leonardo.hotel_search_challenge;

import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;
import org.awaitility.Awaitility;

import java.time.Duration;
import java.util.UUID;

import static com.leonardo.hotel_search_challenge.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class HotelSearchChallengeApplicationIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void cleanDataBase(){
        jdbcTemplate.execute("TRUNCATE TABLE hotel_searches");
    }

    private UUID doPostSearch(SearchRequest request) throws Exception{
        MvcResult result = mockMvc.perform(post("/api/v1/hotels/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.searchId").exists()
                )
                .andReturn();

        return UUID.fromString(
                objectMapper.readTree(result.getResponse().getContentAsString())
                        .get("searchId")
                        .asString()
        );

    }

    private void awaitAndVerifyGetCount(UUID searchId, int expectedCount){
        Awaitility.await()
                .atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofMillis(300))
                .untilAsserted(() ->
                        mockMvc.perform(get("/api/v1/hotels/count")
                                        .param("searchId", searchId.toString())
                                )
                                .andExpectAll(
                                        status().isOk(),
                                        jsonPath("$.searchId").value(searchId.toString()),
                                        jsonPath("$.search.hotelId").value(HOTEL_ID),
                                        jsonPath("$.search.checkIn").value(CHECK_IN_FORMATTED),
                                        jsonPath("$.search.checkOut").value(CHECK_OUT_FORMATTED),
                                        //Testeo cada elemento porque no se puede comparar directamente con el List de edades
                                        jsonPath("$.search.ages[0]").value(AGES.getFirst()),
                                        jsonPath("$.search.ages[1]").value(AGES.get(1)),
                                        jsonPath("$.search.ages[2]").value(AGES.get(2)),
                                        jsonPath("$.search.ages[3]").value(AGES.getLast()),
                                        jsonPath("$.count").value(expectedCount)
                                )
                );
    }

    @Test
    @DisplayName("Flujo E2E: POST /search publica el evento, se consume y persiste. GET /count devuelve 1")
    void should_complete_e2e_post_search_and_get_count() throws Exception{
        UUID searchId = doPostSearch(SEARCH_REQUEST);
        awaitAndVerifyGetCount(searchId, 1);
    }

    @Test
    @DisplayName("Al realizar dos POST /search iguales deben dar count = 2 al hacer el GET /count")
    void should_return_count_two_when_two_equal_searches() throws Exception{
        //Primer POST
        UUID searchId = doPostSearch(SEARCH_REQUEST);

        //Segundo POST
        doPostSearch(SEARCH_REQUEST);

        awaitAndVerifyGetCount(searchId, 2);
    }

    @Test
    @DisplayName("Al realizar dos POST /search con iguales edades pero en distinto orden, " +
            "debe devolver count = 1 al hacer el GET /count")
    void should_return_count_one_when_two_equal_searches_with_different_ages_order() throws Exception{
        //Primer POST
        UUID searchId = doPostSearch(SEARCH_REQUEST);

        //Segundo POST con edades en distinto orden
        doPostSearch(SEARCH_REQUEST_WITH_SAME_AGES_SORTED);

        awaitAndVerifyGetCount(searchId, 1);
    }

    @Test
    @DisplayName("GET /count debe devolver status code 404 cuando no existe el searchId enviado")
    void should_return_404_when_searchId_does_not_exist() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/hotels/count")
                .param("searchId", randomUuid.toString())
        )
                .andExpect(status().isNotFound());
    }

}
