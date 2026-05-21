package com.leonardo.hotel_search_challenge;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	KafkaContainer kafkaContainer() {
		return new KafkaContainer(DockerImageName.parse("apache/kafka:3.8.0"))
				.withStartupTimeout(Duration.ofMinutes(3));
	}

	@Bean
	@ServiceConnection
	OracleContainer oracleFreeContainer() {
        return new OracleContainer(DockerImageName.parse("gvenzl/oracle-free:23-slim"))
                .withStartupTimeout(Duration.ofMinutes(3));
	}

}
