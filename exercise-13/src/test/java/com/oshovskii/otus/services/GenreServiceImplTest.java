package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.interfaces.GenreRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("GenreServiceImpl Test")
@SpringBootTest(classes = GenreServiceImpl.class)
class GenreServiceImplTest {

    @Autowired
    private GenreServiceImpl genreService;

    @MockBean
    private GenreRepository genreRepository;

    private static final String EXISTING_GENRE_ID = "61e9c448ccf1a74f9c05b2f3";
    private static final String EXISTING_GENRE_ID_2 = "61e9c448ccf1a74f9c05b2f8";
    private static final String EXISTING_GENRE_TYPE = "Roman";
    private static final String EXISTING_GENRE_TYPE_2 = "Detective";

    @DisplayName("Return expected list genres test")
    @Test
    void findAllGenres_voidInput_shouldReturnExpectedGenresList() {
        // Config
        val expectedGenre = new Genre();
        expectedGenre.setId(EXISTING_GENRE_ID);
        expectedGenre.setType(EXISTING_GENRE_TYPE);
        val expectedGenre2 = new Genre();
        expectedGenre2.setId(EXISTING_GENRE_ID_2);
        expectedGenre2.setType(EXISTING_GENRE_TYPE_2);

        val expectedGenreList = List.of(expectedGenre, expectedGenre2);

        when(genreRepository.findAll()).thenReturn(expectedGenreList);

        // Call
        val actualGenreList = genreRepository.findAll();

        // Verify
        assertEquals(actualGenreList, expectedGenreList);
    }

    @DisplayName("Return expected genre by type test")
    @Test
    void findGenreByType_validGenreId_shouldReturnExpectedGenreById() {
        // Config
        val expectedGenre = new Genre();
        expectedGenre.setId(EXISTING_GENRE_ID);
        expectedGenre.setType(EXISTING_GENRE_TYPE);

        when(genreRepository.findByType(EXISTING_GENRE_TYPE)).thenReturn(Optional.of(expectedGenre));

        // Call
        val actualGenre = genreService.findGenreByType(expectedGenre.getType());

        // Verify
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DisplayName("Save genre test")
    @Test
    void save_validGenre_shouldSaveGenre() {
        // Config
        val savedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE, List.of());

        when(genreRepository.save(any(Genre.class))).thenReturn(savedGenre);

        // Call
        val actualGenre = genreService.save(savedGenre.getType());

        // Verify
        assertEquals(savedGenre, actualGenre);
        verify(genreRepository, times(1)).save(any(Genre.class));
    }
}
