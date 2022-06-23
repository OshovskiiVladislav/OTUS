package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.GenreRepositoryJpa;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("GenreServiceImpl Test")
@SpringBootTest(classes = GenreServiceImpl.class)
class GenreServiceImplTest {

    @Autowired
    private GenreServiceImpl genreService;

    @MockBean
    private GenreRepositoryJpa genreRepositoryJpa;

    private static final Long EXISTING_GENRE_ID = 1L;
    private static final String EXISTING_GENRE_TYPE = "Detective";

    @DisplayName("Return expected genre by id")
    @Test
    public void getGenreById_validGenreId_shouldReturnExpectedGenreById(){
        // Config
        val expectedGenre = new Genre(EXISTING_GENRE_TYPE);
        expectedGenre.setId(EXISTING_GENRE_ID);

        when(genreRepositoryJpa.findById(EXISTING_GENRE_ID)).thenReturn(Optional.of(expectedGenre));

        // Call
        val actualGenre = genreService.findByGenreId(expectedGenre.getId());

        // Verify
        assertThat(actualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}
