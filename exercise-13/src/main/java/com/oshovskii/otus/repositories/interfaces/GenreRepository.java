package com.oshovskii.otus.repositories.interfaces;

import com.oshovskii.otus.models.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findByType(String type);
}
