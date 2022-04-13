package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.interfaces.BookRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mapping.MappingException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataMongoTest
@EnableConfigurationProperties
@DisplayName("BookRepository Test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private static final String EXISTING_BOOK_ID = "625601c1b9b6c9518777c741";
    private static final String SAVED_BOOK_TITLE = "Test Book";

    @DisplayName("Should return correct book by input title test")
    @Test
    void findBookByTitle_validBookTitle_shouldFindExpectedBookByTitle() {
        // Config
        val savedBook = bookRepository.save(new Book(
                null,
                SAVED_BOOK_TITLE,
                List.of(),
                List.of(),
                List.of()
        ));


        // Call
        val actualBook = bookRepository.findByTitle(SAVED_BOOK_TITLE);

        // Verify
        assertThat(actualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(savedBook);
    }

    @DisplayName("Should throw MappingException on time save book with missing in DB documents")
    @Test
    void shouldThrowMappingExceptionWhenSaveBookWithNewDocuments() {
        val book = new Book(
                null,
                "Book #1",
                List.of(new Author(null, "Knowledge #3")),
                List.of(new Genre(null, "Knowledge #3")),
                List.of(new Comment()));
        assertThatThrownBy(() -> bookRepository.save(book)).isInstanceOf(MappingException.class);
    }
}
