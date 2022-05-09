package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.dto.GenreDto;
import com.oshovskii.otus.models.Genre;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
    Genre findById(Long genreId);
}
