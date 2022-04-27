package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("BookRepository test")
@DataJpaTest
class BookRepositoryTest {

    private final static int EXPECTED_BOOKS_COUNT = 8;
    private final static Author AUTHOR_ONE = new Author(1, "Dan Brown");
    private final static Genre GENRE_ONE = new Genre(1, "Roman");
    private final static String BOOK_ONE_NAME = "THE DA VINCI CODE";
    private final static Book BOOK_ONE = new Book(1, AUTHOR_ONE, GENRE_ONE, BOOK_ONE_NAME);
    private final static long BOOK_ID = 1L;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Should find all books")
    @Test
    void ShouldGetAllBooks() {
        List<Book> books = bookRepository.findAll();
        org.assertj.core.api.Assertions.assertThat(books).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                .allMatch(s -> s.getId() > 0)
                .allMatch(s -> s.getAuthor().getId() > 0)
                .allMatch(s -> !s.getAuthor().getName().equals(""))
                .allMatch(s -> s.getGenre().getId() > 0)
                .allMatch(s -> !s.getGenre().getName().equals(""))
                .allMatch(s -> !s.getTitle().equals(""));
    }

    @DisplayName("Should get correct book")
    @Test
    void shouldGetCorrectBook() {
        Book book = bookRepository.getById(BOOK_ID);
        assertEquals(BOOK_ONE.getId(), book.getId());
    }
}
