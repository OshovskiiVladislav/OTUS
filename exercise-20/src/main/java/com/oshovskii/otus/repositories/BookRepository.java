package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {
}