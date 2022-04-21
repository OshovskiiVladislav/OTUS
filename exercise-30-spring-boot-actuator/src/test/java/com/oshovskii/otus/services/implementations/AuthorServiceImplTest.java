package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.repositories.AuthorRepository;
import com.oshovskii.otus.services.AuthorService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void findAll_voidInput_shouldReturnAuthorList() {
        // Config
        val author = new Author();
        val targetAuthorList = List.of(author);

        when(authorRepository.findAll()).thenReturn(targetAuthorList);

        // Call
        val actualAuthorList = authorService.findAll();

        // Verify
        assertEquals(targetAuthorList, actualAuthorList);
    }

    @Test
    void findById_validAuthorId_shouldReturnAuthor() {
        // Config
        val authorId = 1L;
        val targetAuthor = new Author();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(targetAuthor));

        // Call
        val actualAuthor = authorService.findById(authorId);

        // Verify
        assertEquals(targetAuthor, actualAuthor);
    }
}