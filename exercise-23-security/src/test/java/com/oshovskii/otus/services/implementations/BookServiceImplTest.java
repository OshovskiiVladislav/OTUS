package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.services.implementations.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Books repository and service testing.")
@DataJpaTest
@Import({BookServiceImpl.class})
class BookServiceImplTest {

    private final static int EXPECTED_BOOKS_COUNT = 8;
    private final static Long LONG_ONE = 1L;
    private final static Author AUTHOR_ONE = new Author(LONG_ONE, "Dan Brown");
    private final static Genre GENRE_ONE = new Genre(LONG_ONE, "Roman");
    private final static String BOOK_ONE_NAME = "THE DA VINCI CODE";
    private final static Book BOOK_ONE = new Book(LONG_ONE, AUTHOR_ONE, GENRE_ONE, BOOK_ONE_NAME);

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Should get correct book test")
    @Test
    void shouldGetCorrectBook() {
        Book book = bookService.findById(LONG_ONE);
        assertThat(book).isNotNull()
                .matches(b -> b.getId() == BOOK_ONE.getId())
                .matches(b -> b.getTitle().equals(BOOK_ONE.getTitle()))
                .matches(b -> b.getGenre().equals(GENRE_ONE))
                .matches(b -> b.getAuthor().equals(AUTHOR_ONE));
    }

    @DisplayName("Should find all books test")
    @Test
    void ShouldFindAllBooks() {
        List<Book> books = bookService.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                .allMatch(s -> s.getId() > 0)
                .allMatch(s -> !s.getTitle().equals(""))
                .allMatch(s -> s.getAuthor().getId() > 0)
                .allMatch(s -> !s.getAuthor().getName().equals(""))
                .allMatch(s -> s.getGenre().getId() > 0)
                .allMatch(s -> !s.getGenre().getName().equals(""));
    }

    @DisplayName("Should get correct books count test")
    @Test
    void ShouldGetCorrectBooksCount() {
        assertEquals(EXPECTED_BOOKS_COUNT, bookService.getBooksCount());
    }

    @DisplayName("Should be able to delete a book:")
    @Test
    void shouldDeletefirstBook() {
        Book book = em.find(Book.class, LONG_ONE);
        assertEquals(BOOK_ONE_NAME, book.getTitle());
        bookService.deleteBook(LONG_ONE);
        assertThatCode(() -> bookService.findById(LONG_ONE))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("Should be able to insert new book-1 test")
    @Test
    void shouldInsertNewBook() {
        Book book = new Book(
                0L,
                new Author(LONG_ONE, "Dan Brown"),
                new Genre(LONG_ONE, "Roman"),
                "THE DA VINCI CODE 2"
        );
        Book savedBook = bookService.saveBook(book);

        assertThat(savedBook.getId()).isPositive();
        assertEquals(book.getAuthor(), savedBook.getAuthor());
        assertEquals(book.getGenre(), savedBook.getGenre());
        assertEquals(book.getTitle(), savedBook.getTitle());
    }
}
