package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Author;

import java.util.Optional;

public interface AuthorService {
    Optional<Author> findByAuthorId(Long id);
}
