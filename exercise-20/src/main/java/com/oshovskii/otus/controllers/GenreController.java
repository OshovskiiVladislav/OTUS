package com.oshovskii.otus.controllers;

import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final BookRepository repository;

    @GetMapping("/genres")
    public Flux<Genre> finAll() {
        return repository.findAllGenres();
    }
}