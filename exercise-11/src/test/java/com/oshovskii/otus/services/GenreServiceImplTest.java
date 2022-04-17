package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.GenreRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("GenreServiceImpl Test")
@ExtendWith(SpringExtension.class)
@Import(GenreServiceImpl.class)
class GenreServiceImplTest {

    @Autowired
    private GenreServiceImpl genreService;

    @MockBean
    private GenreRepository genreRepository;

    private static final Long EXISTING_GENRE_ID = 1L;
    private static final String EXISTING_GENRE_TYPE = "Detective";

    @DisplayName("Return expected genre by id test")
    @Test
    void findGenreById_validGenreId_shouldReturnExpectedGenreById() {
        // Config
        val expectedGenre = new Genre(EXISTING_GENRE_TYPE);
        expectedGenre.setId(EXISTING_GENRE_ID);

        when(genreRepository.findById(EXISTING_GENRE_ID)).thenReturn(Optional.of(expectedGenre));

        // Call
        val actualGenre = genreService.findGenreById(expectedGenre.getId());

        // Verify
        assertThat(actualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}
