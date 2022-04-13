package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAllGenres();
    Genre findGenreByType(String type);
    Genre save(String type);
}
