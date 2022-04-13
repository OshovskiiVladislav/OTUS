package com.oshovskii.otus.services;

import com.oshovskii.otus.exceptions.ResourceNotFoundException;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.interfaces.GenreRepository;
import com.oshovskii.otus.services.interfaces.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findGenreByType(String type) {
        return genreRepository.findByType(type)
                .orElseThrow(() -> new ResourceNotFoundException("Genre with type: " + type + " not found"));
    }

    @Override
    public Genre save(String type) {
        Genre genre = new Genre(null, type);
        return genreRepository.save(genre);
    }
}
