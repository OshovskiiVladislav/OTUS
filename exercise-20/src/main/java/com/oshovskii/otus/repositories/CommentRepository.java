package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findByBookId(String bookId);

    Mono<Void> deleteByBookId(String bookId);
}
