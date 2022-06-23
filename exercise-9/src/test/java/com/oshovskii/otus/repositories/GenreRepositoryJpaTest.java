package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Genre;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GenreRepositoryImpl Test ")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
public class GenreRepositoryJpaTest {
    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final Long EXISTING_GENRE_ID = 1L;

    @DisplayName("Should return genre by id")
    @Test
    void findById_validGenreId_shouldFindExpectedGenreById() {
        // Call
        val optionalActualGenre = genreRepositoryJpa.findById(EXISTING_GENRE_ID);

        // Verify
        val expectedGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}
