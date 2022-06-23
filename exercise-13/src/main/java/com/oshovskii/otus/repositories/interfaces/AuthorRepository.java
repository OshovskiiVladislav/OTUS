package com.oshovskii.otus.repositories.interfaces;

import com.oshovskii.otus.models.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByName(String name);
}
