package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.exceptions.ResourceNotFoundException;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.GenreRepository;
import com.oshovskii.otus.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private static final String GENRE_NOT_EXIST = "Didn't find genre";

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Genre findById(long id) {
        Genre genreById = genreRepository.findById(id).orElse(null);
        if (genreById != null) {
            return genreById;
        }
        throw new ResourceNotFoundException(GENRE_NOT_EXIST);
    }

    @Transactional
    @Override
    public void save(long id, String name) {
        Genre genre = genreRepository.findById(id).orElse(null);
        if (genre == null) {
            throw new ResourceNotFoundException(GENRE_NOT_EXIST);
        }
        genre.setName(name);
        genreRepository.save(genre);
    }

    @Transactional
    @Override
    public long create(String name) {
        Genre genre = new Genre(0, name);
        return genreRepository.save(genre).getId();
    }

    @Transactional
    @Override
    public void delete(long genreId) {
        genreRepository.findById(genreId).ifPresent(genreRepository::delete);
    }
}
