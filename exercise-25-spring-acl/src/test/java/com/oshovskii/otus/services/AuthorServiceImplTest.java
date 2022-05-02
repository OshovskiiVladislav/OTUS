package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.repositories.AuthorRepository;
import com.oshovskii.otus.services.implementations.AuthorServiceImpl;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("AuthorServiceImpl test")
@ExtendWith(SpringExtension.class)
@Import(AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @SpyBean
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("findByName() " +
            "with valid author name " +
            "should return expected author test")
    void findByName_validAuthorName_shouldReturnAuthor() {
        // Config
        val authorName = "name_test";
        val expectedAuthor = new Author();
        expectedAuthor.setName(authorName);

        when(authorRepository.findByName(authorName)).thenReturn(Optional.of(expectedAuthor));

        // Call
        val actualAuthor = authorService.findByName(authorName);

        // Verify
        assertEquals(expectedAuthor, actualAuthor);
    }
}