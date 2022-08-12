package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.GenreDto;
import com.oshovskii.otus.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.GenreRepository;
import com.oshovskii.otus.services.interfaces.GenreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(genre -> modelMapper.map(genre, GenreDto.class))
                .collect(Collectors.toList());
    }

    public Genre findById(Long genreId) {
        return genreRepository.findById(genreId).orElseThrow(
                () -> new ResourceNotFoundException("Genre by id: " + genreId + " not found" ));
    }
}
