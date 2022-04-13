package com.oshovskii.otus.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository Test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private static final Long EXISTING_BOOK_ID = 2L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";

    @DisplayName("Should upload to correct book by input name test")
    @Test
    void findByTitle_validBookTitle_shouldFindExpectedBookByTitle() {
        // Config
        val expectedBook = bookRepository.findById(EXISTING_BOOK_ID);

        // Call
        val actualBook = bookRepository.findByTitle(EXISTING_BOOK_TITLE);

        // Verify
        assertThat(actualBook).isEqualTo(List.of(expectedBook.get()));
    }
}
