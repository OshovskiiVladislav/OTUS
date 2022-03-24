package com.oshovskii.otus.controllers;

import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository repository;

    @ResponseStatus(CREATED)
    @PostMapping("/comments")
    public Mono<Comment> add(@RequestBody Comment comment) {
        return repository.save(comment);
    }

    @GetMapping("/books/{bookId}/comments")
    public Flux<Comment> findByBookId(@PathVariable("bookId") String bookId) {
        return repository.findByBookId(bookId);
    }

    @DeleteMapping("/comments/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return repository.deleteById(id);
    }
}
