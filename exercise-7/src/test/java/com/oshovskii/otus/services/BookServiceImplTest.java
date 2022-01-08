package com.oshovskii.otus.services;

import com.oshovskii.otus.dao.BookDaoImpl;
import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("BookServiceImpl Test")
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {


    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookDaoImpl bookDao;

    private static final int EXPECTED_BOOKS_COUNT = 2;
    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final String EXISTING_AUTHOR_NAME_2 = "Dan Brown";
    private static final String EXISTING_AUTHOR_NAME_1 = "Dan Brown";
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "Angels and Demons";
    private static final String EXISTING_GENRE_NAME = "Detective";
    private static final String EXISTING_GENRE_NAME_2 = "Detective";

    @DisplayName("Return expected count books in db")
    @Test
    public void countAllBook_voidInput_shouldReturnExpectedBookCount(){
        // Config
        when(bookDao.count()).thenReturn(EXPECTED_BOOKS_COUNT);

        // Call
        final int actualBookCount = bookService.countAllBook();

        // Verify
        assertEquals(actualBookCount, EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Add book in db")
    @Test
    public void insert_validBook_shouldInsertBook(){
        // Config
        Book saveBook = new Book(EXISTING_BOOK_TITLE_2);
        Author saveAuthor = new Author(EXISTING_AUTHOR_NAME_2);
        Genre saveGenre = new Genre(EXISTING_GENRE_NAME_2);
        doNothing().when(bookDao).insert(saveBook, saveAuthor, saveGenre);

        // Call
        bookService.insertBook(saveBook, saveAuthor, saveGenre);

        // Verify
        verify(bookDao, times(1)).insert(saveBook, saveAuthor, saveGenre);
    }

    @DisplayName("Return expected book by id")
    @Test
    public void getBookById_validBookId_shouldReturnExpectedBookById(){
        // Config
        Book expectedBook = new Book(EXISTING_BOOK_TITLE_2);
        expectedBook.setId(EXISTING_BOOK_ID_2);

        Author author = new Author(EXISTING_GENRE_NAME_2);
        author.setBooks(Set.of(expectedBook));
        Genre genre = new Genre(EXISTING_GENRE_NAME_2);
        genre.setBooks(Set.of(expectedBook));

        when(bookDao.getById(EXISTING_BOOK_ID_2)).thenReturn(expectedBook);

        // Call
        final Book actualBook = bookService.getBookById(expectedBook.getId());

        // Verify
        assertEquals(actualBook, expectedBook);
    }

    @DisplayName("Return expected list books")
    @Test
    public void getAllBook_voidInput_shouldReturnExpectedBooksList(){
        // Config
        // create book 1
        Book expectedBook = new Book(EXISTING_BOOK_TITLE_2);
        expectedBook.setId(EXISTING_BOOK_ID_2);

        Author author = new Author(EXISTING_GENRE_NAME_2);
        author.setBooks(Set.of(expectedBook));
        Genre genre = new Genre(EXISTING_GENRE_NAME_2);
        genre.setBooks(Set.of(expectedBook));

        // create book 2
        Book expectedBook2 = new Book(EXISTING_BOOK_TITLE);
        expectedBook.setId(EXISTING_BOOK_ID);
        Author author2 = new Author(EXISTING_AUTHOR_NAME_1);
        author2.setBooks(Set.of(expectedBook));
        Genre genre2 = new Genre(EXISTING_GENRE_NAME);
        genre2.setBooks(Set.of(expectedBook));

        List<Book> expectedBookList = List.of(expectedBook, expectedBook2);
        when(bookDao.getAll()).thenReturn(expectedBookList);

        // Call
        final List<Book> actualBookList = bookService.getAllBook();

        // Verify
        assertEquals(actualBookList, expectedBookList);
    }

    @DisplayName("Delete book by id")
    @Test
    public void deleteBookById_validId_shouldCorrectDeleteBookById(){
        // Config
        doNothing().when(bookDao).deleteById(EXISTING_BOOK_ID);

        // Call
        bookService.deleteBookById(EXISTING_BOOK_ID);

        // Verify
        verify(bookDao, times(1)).deleteById(EXISTING_BOOK_ID);
    }
}
