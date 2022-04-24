package com.oshovskii.otus.services;

import com.oshovskii.otus.models.GenreDto;
import com.oshovskii.otus.repositories.GenreRepository;
import com.oshovskii.otus.services.interfaces.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreDto> findGenreById(Long id) {
        return genreRepository.findById(id);
    }
}
