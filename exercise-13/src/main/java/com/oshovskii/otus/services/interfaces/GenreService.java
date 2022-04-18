package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Genre;

public interface GenreService {
    Genre findGenreByType(String type);
    Genre save(String type);
}
