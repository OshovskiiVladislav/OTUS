package com.oshovskii.otus.controllers;

import com.oshovskii.otus.models.Comment;
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
import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@DisplayName("Контроллер для работы с комментариями должен ")
@WebFluxTest
@ContextConfiguration(classes = {CommentController.class})
class CommentControllerTest {
    private static final String ID = ObjectId.get().toString();

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentRepository repository;

    @DisplayName("возвращать список комментариев для книги ")
    @Test
    void findByBookIdTest() {
        webTestClient.get()
                .uri(format("/books/%s/comments", ObjectId.get()))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @DisplayName("добавлять коментарий")
    @Test
    void saveTest() {
        webTestClient.post()
                .uri("/comments")
                .bodyValue(new Comment())
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @DisplayName("удалять коментарий")
    @Test
    void deleteTest() {
        given(repository.deleteById(anyString())).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/comments/" + ID)
                .exchange()
                .expectStatus()
                .isOk();
    }
}