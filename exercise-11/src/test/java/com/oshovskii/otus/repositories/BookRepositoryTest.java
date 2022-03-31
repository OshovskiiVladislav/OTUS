package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Book;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository Test")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";

    @DisplayName("Should return correct book by input title test")
    @Test
    void findBookByTitle_validBookTitle_shouldFindExpectedBookByTitle() {
        // Config
        val expectedBook = testEntityManager.find(Book.class ,EXISTING_BOOK_ID);

        // Call
        val actualBook = bookRepository.findBookByTitle(EXISTING_BOOK_TITLE);

        // Verify
        assertThat(expectedBook).isEqualTo(actualBook);
    }
}
