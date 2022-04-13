package com.oshovskii.otus.repositories.interfaces;

import com.oshovskii.otus.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> { // TODO, BookRepositoryCustom
    Optional<Book> findByTitle(String title);
}
