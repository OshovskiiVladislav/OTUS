package com.oshovskii.otus.repositories.interfaces;

import com.oshovskii.otus.models.Author;

import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findById(Long id);
}
