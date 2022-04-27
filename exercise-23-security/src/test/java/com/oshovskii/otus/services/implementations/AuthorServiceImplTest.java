package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.services.AuthorService;
import com.oshovskii.otus.services.implementations.AuthorServiceImpl;
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

@DisplayName("Authors repository and service test")
@DataJpaTest
@Import(AuthorServiceImpl.class)
class AuthorServiceImplTest {

    private final static int EXPECTED_AUTHORS_COUNT = 4;
    private final static long AUTHOR_ONE_ID = 1L;
    private final static String AUTHOR_ONE_NAME = "Dan Brown";
    private final static String AUTHOR_ONE_NAME_NEW = "New test Dan Brown";
    private final static Author AUTHOR_ONE_UPDATED = new Author(AUTHOR_ONE_ID, AUTHOR_ONE_NAME_NEW);

    @Autowired
    private AuthorService authorService;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Should get correct Author")
    @Test
    void shouldGetCorrectAuthor() {
        Author author = authorService.findById(AUTHOR_ONE_ID);
        assertEquals(AUTHOR_ONE_ID, author.getId());
        assertEquals(AUTHOR_ONE_NAME, author.getName());
    }


    @DisplayName("Should find all Authors")
    @Test
    void ShouldGetAllAuthors() {
        val authors = authorService.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_AUTHORS_COUNT)
                .allMatch(s -> s.getId() > 0)
                .allMatch(s -> !s.getName().equals(""));
    }


    @DisplayName("Should be able to insert a Author-1 after deletions test")
    @Test
    void shouldAddNewAuthor() {
        Author savedAuthor = authorService.create(AUTHOR_ONE_NAME_NEW);
        assertThat(savedAuthor.getId()).isPositive();
        assertEquals(AUTHOR_ONE_NAME_NEW, authorService.findById(savedAuthor.getId()).getName());
    }


    @DisplayName("Add author test")
    @Test
    void shouldUpdateAuthor() {
        authorService.save(AUTHOR_ONE_ID, AUTHOR_ONE_NAME_NEW);
        Author authorNew = em.find(Author.class, AUTHOR_ONE_ID);
        assertEquals(AUTHOR_ONE_UPDATED, authorNew);
    }


    @DisplayName("Should be able to delete a Author:")
    @Test
    void shouldDeleteFirstAuthor() {
        Author author = authorService.findById(AUTHOR_ONE_ID);
        assertEquals(AUTHOR_ONE_NAME, author.getName());
        authorService.delete(author.getId());
        assertThatCode(() -> authorService.findById(AUTHOR_ONE_ID))
                .isInstanceOf(RuntimeException.class);
    }
}
