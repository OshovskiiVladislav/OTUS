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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.mapping.MappingException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@EnableConfigurationProperties
@DisplayName("BookRepository Test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private static final String EXISTING_BOOK_ID = "61e962590f8ce7592de50e9a";
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";

    @DisplayName("Should return correct book by input title test")
    @Test
    void findBookByTitle_validBookTitle_shouldFindExpectedBookByTitle() {
        // Config
        val expectedBook = bookRepository.findById(EXISTING_BOOK_ID);

        // Call
        val actualBook = bookRepository.findByTitle(EXISTING_BOOK_TITLE);

        // Verify
        assertThat(expectedBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(actualBook.get());
    }

    @DisplayName("Should throw MappingException on time save book with missing in DB documents")
    @Test
    void shouldThrowMappingExceptionWhenSaveBookWithNewDocuments(){
        val student = new Book(
                null,
                "Book #1",
                List.of(new Author(null ,"Knowledge #3", List.of())),
                List.of(new Genre(null ,"Knowledge #3", List.of())),
                List.of(new Comment()));
        assertThatThrownBy(() -> bookRepository.save(student)).isInstanceOf(MappingException.class);
    }
}
