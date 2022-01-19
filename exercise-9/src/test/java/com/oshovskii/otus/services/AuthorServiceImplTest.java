package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.repositories.AuthorRepositoryJpa;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("AuthorServiceImpl Test")
@SpringBootTest(classes = AuthorServiceImpl.class)
public class AuthorServiceImplTest {
    @Autowired
    private AuthorServiceImpl authorService;

    @MockBean
    private AuthorRepositoryJpa authorRepositoryJpa;

    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";

    @DisplayName("Return expected author by id")
    @Test
    public void getAuthorById_validAuthorId_shouldReturnExpectedAuthorById(){
        // Config
        val expectedAuthor = new Author(EXISTING_AUTHOR_NAME);
        expectedAuthor.setId(EXISTING_AUTHOR_ID);

        when(authorRepositoryJpa.findById(EXISTING_AUTHOR_ID)).thenReturn(Optional.of(expectedAuthor));

        // Call
        val actualAuthor = authorService.findByAuthorId(expectedAuthor.getId());

        // Verify
        assertThat(actualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}
