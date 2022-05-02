package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.GenreRepository;
import com.oshovskii.otus.services.implementations.GenreServiceImpl;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("findByName() " +
            "with valid genre name " +
            "should return expected genre test")
    void findByName_validGenreName_shouldReturnGenre() {
        // Config
        val genreName = "genre_type";
        val expectedGenre = new Genre();

        when(genreRepository.findByName(genreName)).thenReturn(Optional.of(expectedGenre));

        // Call
        val actualGenre = genreService.findByName(genreName);

        // Verify
        assertEquals(expectedGenre, actualGenre);
    }
}
