package com.oshovskii.otus.repositories;

import com.oshovskii.otus.repositories.interfaces.CommentRepository;
import com.oshovskii.otus.repositories.interfaces.GenreRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GenreRepository Test")
@EnableConfigurationProperties
@DataMongoTest
class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    private static final String EXISTING_GENRE_ID = "61e9c448ccf1a74f9c05b2f8";
    private static final String EXISTING_GENRE_TYPE = "Detective";


    @DisplayName("Should return correct genre by input type test")
    @Test
    void findByType_expectedValidGenreType_shouldFindExpectedGenreByType() {
        // Config
        val expectedGenre = genreRepository.findById(EXISTING_GENRE_ID);
        // Call
        val actualGenre = genreRepository.findByType(EXISTING_GENRE_TYPE);

        // Verify
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }
}