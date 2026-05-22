package com.leonardo.hotel_search_challenge.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Hotel Search Challenge API")
                        .description("API para registrar búsquedas de hoteles y consultar coincidencias de búsquedas")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Leonardo Gil")
                                .email("leonardogil2604@gmail.com")
                        )
                )
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor local")
                ));
    }

}
