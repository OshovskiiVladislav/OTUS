package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.GenreRepository;
import com.oshovskii.otus.services.GenreService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("GenreService test")
@ExtendWith(SpringExtension.class)
@Import(GenreServiceImpl.class)
class GenreServiceImplTest {

    @SpyBean
    private GenreService genreService;

    @MockBean
    private GenreRepository genreRepository;

    @Test
    void findAll_voidInput_shouldReturnGenreList() {
        //Config
        val genre = new Genre();
        val expectedGenresList = List.of(genre);

        when(genreRepository.findAll()).thenReturn(expectedGenresList);

        // Call
        val actualGenreList = genreService.findAll();

        // Verify
        assertEquals(expectedGenresList, actualGenreList);
    }

    @Test
    void findById_validGenreId_shouldReturnGenre() {
        // Config
        val genreId = 1L;
        val expectedGenre = new Genre();

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(expectedGenre));

        // Call
        val actualGenre = genreService.findById(genreId);

        // Verify
        assertEquals(expectedGenre, actualGenre);
    }
}
