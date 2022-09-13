package com.poc.library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookIT {

  @Autowired
  WebTestClient webClient;

  String url = "/control/3/mark/7?conclusion=4&infect=-5";

  @Test
  @DisplayName("Actual request body fields constraints")
  void invalidBody() throws Exception {

    Book request = new Book();
    request.setAddress("");
    request.setMail("pier.com");

    webClient.post().uri(url)
        .contentType(APPLICATION_JSON)
        .body(Mono.just(request), Book.class)
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody(String.class).isEqualTo("{\"address\":\"must not be blank\",\"mail\":\"must be a well-formed email address\",\"floor\":\"floor cannot be null\"}");
  }

  @Test
  @DisplayName("Actual query and path parameters constraints")
  void invalidQueryPathParameters() throws Exception {

    Book request = new Book();
    request.setAddress("lima");
    request.setFloor("eleven");
    request.setMail("customer@domain.com");

    webClient.post().uri(url)
        .contentType(APPLICATION_JSON)
        .body(Mono.just(request), Book.class)
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody(String.class).isEqualTo("{\"control.mark\":\"must be less than or equal to 5\",\"control.infect\":\"must be greater than 0\",\"control.age\":\"must be greater than or equal to 5\"}");
  }

  @Test
  @DisplayName("Expected response body")
  void todo() throws Exception {

    Book request = new Book();
    request.setAddress("");
    request.setMail("pier.com");

    webClient.post().uri(url)
        .contentType(APPLICATION_JSON)
        .body(Mono.just(request), Book.class)
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody(String.class).isEqualTo("{\"address\":\"must not be blank\",\"mail\":\"must be a well-formed email address\",\"floor\":\"floor cannot be null\",\"control.mark\":\"must be less than or equal to 5\",\"control.infect\":\"must be greater than 0\",\"control.age\":\"must be greater than or equal to 5\"}");
  }
}
