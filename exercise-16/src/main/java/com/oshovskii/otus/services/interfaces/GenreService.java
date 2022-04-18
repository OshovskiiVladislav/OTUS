package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
