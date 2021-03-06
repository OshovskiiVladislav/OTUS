package com.oshovskii.otus.controllers;

import com.oshovskii.otus.repositories.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@DisplayName("Контроллер для работы с жанрами должен ")
@WebFluxTest
@ContextConfiguration(classes = {GenreController.class})
class GenreControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository repository;

    @DisplayName("возвращать список жанров")
    @Test
    public void findAllTest() {
        webTestClient.get()
                .uri("/genres")
                .exchange()
                .expectStatus()
                .isOk();
    }
}