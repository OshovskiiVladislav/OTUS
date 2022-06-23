package com.oshovskii.otus.controllers;

import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.repositories.BookRepository;
import com.oshovskii.otus.repositories.CommentRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@DisplayName("Контроллер для работы с книгами должен ")
@WebFluxTest
@ContextConfiguration(classes = {BookController.class})
class BookControllerTest {

    private static final String ID = ObjectId.get().toString();

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository repository;

    @MockBean
    private CommentRepository commentRepository;

    @DisplayName("возвращать книгу по id")
    @Test
    void findOneTest() {
        webTestClient.get()
                .uri("/books/" + ID)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @DisplayName("возвращать список книг")
    @Test
    void findAllTest() {
        webTestClient.get()
                .uri("/books")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @DisplayName("добавлять книгу")
    @Test
    void addTest() {
        webTestClient.post()
                .uri("/books")
                .bodyValue(new Book())
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @DisplayName("редактировать книгу")
    @Test
    void updateTest() {
        webTestClient.put()
                .uri("/books")
                .bodyValue(new Book())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @DisplayName("удалять книгу")
    @Test
    public void deleteTest() {
        given(repository.deleteById(anyString())).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/books/" + ID)
                .exchange()
                .expectStatus()
                .isOk();
    }
}