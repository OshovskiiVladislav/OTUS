package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Genre;

import java.util.Optional;

public interface GenreService {
    Optional<Genre> findGenreById(Long id);
}
