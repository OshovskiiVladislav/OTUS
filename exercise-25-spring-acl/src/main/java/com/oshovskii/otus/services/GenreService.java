package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Genre;

public interface GenreService {
    Genre findByName(String genreName);
}
