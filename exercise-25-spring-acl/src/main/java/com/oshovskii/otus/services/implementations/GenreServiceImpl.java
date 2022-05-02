package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.GenreRepository;
import com.oshovskii.otus.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @Override
    public Genre findByName(String genreName) {
        return genreRepository.findByName(genreName)
                .orElseThrow(
                        () -> new ResourceNotFoundException(format("GenreName with name: [%s] not found", genreName))
                );
    }
}
