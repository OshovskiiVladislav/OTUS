package com.oshovskii.otus.repositories;

import com.oshovskii.otus.repositories.interfaces.AuthorRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthorRepository Test")
@DataMongoTest
class
AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    private static final String EXISTING_AUTHOR_ID = "61e9c448ccf1a74f9c05b2f6";
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";

    @DisplayName("Should return correct author by name test")
    @Test
    void findByName_expectedValidAuthorName_shouldFindExpectedAuthorByName() {
        // Config
        val expectedAuthor = authorRepository.findById(EXISTING_AUTHOR_ID);
        // Call
        val actualAuthor = authorRepository.findByName(EXISTING_AUTHOR_NAME);

        // Verify
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }
}