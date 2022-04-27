package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.services.GenreService;
import com.oshovskii.otus.services.implementations.GenreServiceImpl;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Genres repository with service test")
@DataJpaTest
@Import(GenreServiceImpl.class)
class GenreServiceImplTest {
    private final static int EXPECTED_GENRES_COUNT = 4;
    private final static long GENRE_ONE_ID = 1L;
    private final static String GENRE_ONE_NAME = "Roman";
    private final static String GENRE_ONE_NAME_NEW = "Roman - New";
    private final static Genre GENRE_NEW_UPDATED = new Genre(GENRE_ONE_ID, GENRE_ONE_NAME_NEW);

    @Autowired
    private GenreService genreService;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Should get correct Genre test")
    @Test
    void shouldGetCorrectGenre() {
        Genre genre = genreService.findById(GENRE_ONE_ID);

        assertEquals(GENRE_ONE_ID, genre.getId());
        assertEquals(GENRE_ONE_NAME, genre.getName());
    }

    @DisplayName("Should find all Genres")
    @Test
    void ShouldGetAllGenres() {
        val genres = genreService.findAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_GENRES_COUNT)
                .allMatch(s -> s.getId() > 0)
                .allMatch(s -> !s.getName().equals(""));
    }

    @DisplayName("Add author test")
    @Test
    void shouldUpdateAuthor() {
        genreService.save(GENRE_ONE_ID, GENRE_ONE_NAME_NEW);
        val authorNew = em.find(Genre.class, GENRE_ONE_ID);
        assertEquals(GENRE_NEW_UPDATED, authorNew);
    }


    @DisplayName("Should be able to delete a Genre test")
    @Test
    void shouldDeletefirstGenre() {
        val genre = em.find(Genre.class, GENRE_ONE_ID);
        assertEquals(GENRE_ONE_NAME, genre.getName());
        genreService.delete(GENRE_ONE_ID);
        assertThatCode(() -> genreService.findById(GENRE_ONE_ID))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("Should be able to insert a Genre-1 after deletions test")
    @Test
    void shouldAddNewGenre() {
        long savedGenreId = genreService.create(GENRE_ONE_NAME_NEW);
        assertThat(savedGenreId).isPositive();
        assertEquals(GENRE_ONE_NAME_NEW, genreService.findById(savedGenreId).getName());
    }
}