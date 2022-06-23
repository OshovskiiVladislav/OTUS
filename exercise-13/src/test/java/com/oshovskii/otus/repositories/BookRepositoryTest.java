package com.oshovskii.otus.repositories;

import com.oshovskii.otus.repositories.interfaces.BookRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
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
//        val ex = bookRepository.findAll();
//        System.out.println(bookRepository.findAll());
        // Call
        val actualBook = bookRepository.findByTitle(EXISTING_BOOK_TITLE);

        // Verify
        assertThat(expectedBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(actualBook.get());
    }
}