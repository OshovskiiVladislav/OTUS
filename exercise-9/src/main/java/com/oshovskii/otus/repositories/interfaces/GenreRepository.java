package com.oshovskii.otus.repositories.interfaces;

import com.oshovskii.otus.models.Genre;

import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> findById(Long id);
}
