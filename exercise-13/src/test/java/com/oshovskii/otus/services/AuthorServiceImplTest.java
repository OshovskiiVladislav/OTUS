package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.repositories.interfaces.AuthorRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("AuthorServiceImpl Test")
@SpringBootTest(classes = AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl authorService;

    @MockBean
    private AuthorRepository authorRepository;

    private static final String EXISTING_AUTHOR_ID = "61e9c448ccf1a74f9c05b2f6";
    private static final String EXISTING_AUTHOR_ID_2 = "61e9c448ccf1a74f9c05b2f7";
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";
    private static final String EXISTING_AUTHOR_NAME_2 = "Dan BrownScott Fitzgerald";

    @DisplayName("Return expected list authors test")
    @Test
    void findAllAuthors_voidInput_shouldReturnExpectedAuthorsList() {
        // Config
        val expectedAuthor = new Author();
        expectedAuthor.setId(EXISTING_AUTHOR_ID);
        expectedAuthor.setName(EXISTING_AUTHOR_NAME);
        val expectedAuthor2 = new Author();
        expectedAuthor2.setId(EXISTING_AUTHOR_ID_2);
        expectedAuthor2.setName(EXISTING_AUTHOR_NAME_2);

        val expectedAuthorList = List.of(expectedAuthor, expectedAuthor2);

        when(authorRepository.findAll()).thenReturn(expectedAuthorList);

        // Call
        val actualAuthorList = authorRepository.findAll();

        // Verify
        assertEquals(actualAuthorList, expectedAuthorList);
    }

    @DisplayName("Return expected author by name test")
    @Test
    void findAuthorById_validAuthorId_shouldReturnExpectedAuthorById() {
        // Config
        val expectedAuthor = new Author();
        expectedAuthor.setId(EXISTING_AUTHOR_ID);
        expectedAuthor.setName(EXISTING_AUTHOR_NAME);

        when(authorRepository.findByName(EXISTING_AUTHOR_NAME)).thenReturn(Optional.of(expectedAuthor));

        // Call
        val actualAuthor = authorService.findAuthorByName(expectedAuthor.getName());

        // Verify
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName("Save author test")
    @Test
    void save_validName_shouldSaveAuthor() {
        // Config
        val savedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, List.of());

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        // Call
        val actualAuthor = authorService.save(savedAuthor.getName());

        // Verify
        assertEquals(savedAuthor, actualAuthor);
        verify(authorRepository, times(1)).save(any(Author.class));
    }
}
