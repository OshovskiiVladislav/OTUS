package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Genre;
import reactor.core.publisher.Flux;


public interface BookRepositoryCustom {
    Flux<Author> findAllAuthors();

    Flux<Genre> findAllGenres();
}