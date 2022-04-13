package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.repositories.interfaces.AuthorRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorRepository Test")
@EnableConfigurationProperties
@DataMongoTest
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    private static final String SAVED_AUTHOR_NAME = "TestAuthor";

    @DisplayName("Should return correct author by name test")
    @Test
    void findByName_expectedValidAuthorName_shouldFindExpectedAuthorByName() {
        // Config
        val savedAuthor = authorRepository.save(new Author(null, SAVED_AUTHOR_NAME));

        // Call
        val actualAuthor = authorRepository.findByName(SAVED_AUTHOR_NAME);

        // Verify
        assertThat(actualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(savedAuthor);
    }
}
