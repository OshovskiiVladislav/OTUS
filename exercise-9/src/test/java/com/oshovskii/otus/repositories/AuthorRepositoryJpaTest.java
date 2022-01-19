package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Author;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorRepositoryJpa Test ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
public class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final Long EXISTING_AUTHOR_ID = 1L;

    @DisplayName("Should return author by id")
    @Test
    void findById_validAuthorId_shouldFindExpectedAuthorById() {
        // Call
        val optionalActualAuthor = authorRepositoryJpa.findById(EXISTING_AUTHOR_ID);

        // Verify
        val expectedAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(optionalActualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}
