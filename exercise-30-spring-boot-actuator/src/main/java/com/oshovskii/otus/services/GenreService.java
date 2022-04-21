package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();
    Genre findById(Long id);
}
