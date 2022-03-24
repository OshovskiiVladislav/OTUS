package com.oshovskii.otus.controllers;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final BookRepository repository;

    @GetMapping("/authors")
    public Flux<Author> finAll() {
        return repository.findAllAuthors();
    }
}
