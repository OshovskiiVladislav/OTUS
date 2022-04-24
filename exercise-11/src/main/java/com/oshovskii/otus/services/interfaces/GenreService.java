package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.GenreDto;

import java.util.Optional;

public interface GenreService {
    Optional<GenreDto> findGenreById(Long id);
}
