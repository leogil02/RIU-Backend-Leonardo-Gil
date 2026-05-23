package com.leonardo.hotel_search_challenge;

import com.leonardo.hotel_search_challenge.application.port.in.SaveSearchUseCase;
import com.leonardo.hotel_search_challenge.domain.model.PersistedHotelSearch;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

import static com.leonardo.hotel_search_challenge.TestData.SEARCH_REQUEST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
public class HotelSearchedEventConsumerDltIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SaveSearchUseCase saveSearchUseCase;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void cleanDataBase(){
        jdbcTemplate.execute("TRUNCATE TABLE hotel_searches");
    }

    @Test
    @DisplayName("Cuando el consumer falle 5 veces, el mensaje debe ir al DLT")
    void should_send_to_dlt_when_consumer_fails_five_times() throws Exception {

        //Necesito mockear si o si para simular la excepción
        doThrow(new RuntimeException("Simulando error para DLT..."))
                .when(saveSearchUseCase).save(any(PersistedHotelSearch.class));

        //Este POST va a lanzar excepción por el mock a .save
        mockMvc.perform(post("/api/v1/hotels/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SEARCH_REQUEST))
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.searchId").exists()
                )
                .andReturn();

        Awaitility.await()
                .atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofMillis(500))
                .untilAsserted(() ->
                        verify(saveSearchUseCase, times(5)).save(any(PersistedHotelSearch.class)));

    }

}
