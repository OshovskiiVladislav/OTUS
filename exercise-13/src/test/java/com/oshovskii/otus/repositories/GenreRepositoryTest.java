package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.interfaces.GenreRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GenreRepository Test")
@EnableConfigurationProperties
@DataMongoTest
class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    private static final String SAVED_GENRE_TYPE = "TestGenre";


    @DisplayName("Should return correct genre by input type test")
    @Test
    void findByType_expectedValidGenreType_shouldFindExpectedGenreByType() {
        // Config
        val savedGenre = genreRepository.save(new Genre(null, SAVED_GENRE_TYPE));

        // Call
        val actualGenre = genreRepository.findByType(SAVED_GENRE_TYPE);

        // Verify
        assertThat(actualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(savedGenre);
    }
}
