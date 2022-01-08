package com.oshovskii.otus.dao;

import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("BookDaoImpl Test")
@JdbcTest
@Import(BookDaoImpl.class)
public class BookDaoImplTest {

    @Autowired
    private BookDaoImpl bookDao;

    private static final int EXPECTED_BOOKS_COUNT = 2;
    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_BOOK_ID_3 = 3L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";

    @DisplayName("Return expected count books in db")
    @Test
    void count_voidInput_shouldReturnExpectedBookCount() {
        // Call
        final int actualPersonsCount = bookDao.count();

        // Verify
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Return expected book by id")
    @Test
    void getById_validBookId_shouldReturnExpectedBookById() {
        // Call
        final Book actualBook = bookDao.getById(EXISTING_BOOK_ID);

        // Verify
        assertEquals(EXISTING_BOOK_TITLE, actualBook.getTitle());
    }

    @DisplayName("Add book in db")
    @Test
    void insert_validBookAndAuthorAndGenre_shouldInsertBook() {
        // Config
        Book saveBook = new Book("The Great Gatsby");
        Author saveAuthor = new Author("Smith");
        Genre saveGenre = new Genre("Roman");

        // Call
        bookDao.insert(saveBook, saveAuthor, saveGenre);

        // Verify
        Book actualBook = bookDao.getById(EXISTING_BOOK_ID_3);
        assertEquals(saveBook.getTitle(), actualBook.getTitle());
    }

    @DisplayName("Delete book by id")
    @Test
    void deleteById_validId_shouldCorrectDeleteBookById() {
        // Config
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        // Call
        bookDao.deleteById(EXISTING_BOOK_ID);

        // Verify
        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Return expected list books")
    @Test
    void getAll_voidInput_shouldReturnExpectedBooksList() {
        // Call
        final List<Book> actualBookList = bookDao.getAll();

        // Verify
        assertEquals(actualBookList.size(), EXPECTED_BOOKS_COUNT);
    }
}
