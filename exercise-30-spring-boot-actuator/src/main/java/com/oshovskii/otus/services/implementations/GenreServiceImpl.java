package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.GenreRepository;
import com.oshovskii.otus.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    public static final String GENRE_NOT_FOUND = "Genre not found. Id = %s";
    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Genre findById(Long id) {
        return genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(format(GENRE_NOT_FOUND, id)));
    }
}
