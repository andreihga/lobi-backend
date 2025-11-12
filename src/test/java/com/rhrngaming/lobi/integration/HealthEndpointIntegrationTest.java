package com.rhrngaming.lobi.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource(properties = "management.health.probes.enabled=true")
@Testcontainers
class HealthEndpointIntegrationTest {

  @Autowired private WebTestClient webTestClient;

  /*
  We can ignore "'PostgreSQLContainer<SELF>' used without 'try'-with-resources statement " because
  @Container will handle the lifecycle of the container, no need to have it closed by 'try'-with-resources
  */
  @Container
  static final PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:16")
          .withDatabaseName("lobi_test")
          .withUsername("test_user")
          .withPassword("test_password");

  @DynamicPropertySource
  static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Test
  void test_health_endpoint() {
    webTestClient.get().uri("actuator/lobi-backend").exchange().expectStatus().isOk();
  }
}
