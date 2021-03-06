package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.BookRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@DisplayName("BookServiceImpl Test")
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @MockBean
    private CommentServiceImpl commentService;

    private static final Long EXPECTED_BOOKS_COUNT = 2L;
    private static final String EXPECTED_BOOK_TITLE = "Test title";
    private static final Long EXPECTED_BOOK_ID = 0L;

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "Angels and Demons";

    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final Long EXISTING_AUTHOR_ID_2 = 2L;
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";
    private static final String EXISTING_AUTHOR_NAME_2 = "Dan Brown";

    private static final Long EXISTING_GENRE_ID = 1L;
    private static final Long EXISTING_GENRE_ID_2 = 2L;
    private static final String EXISTING_GENRE_TYPE = "Detective";
    private static final String EXISTING_GENRE_TYPE_2 = "Roman";

    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final Long EXISTING_COMMENT_ID_2 = 2L;
    private static final String EXISTING_COMMENT_TEXT = "Good book";
    private static final String EXISTING_COMMENT_TEXT_2 = "The best book";

    @DisplayName("Return expected book by id test")
    @Test
    public void findBookById_validBookId_shouldReturnExpectedBookById(){
        // Config
        val expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        val expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE);
        val expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        val expectedBook = new Book(EXISTING_BOOK_TITLE);
        expectedBook.setId(EXISTING_BOOK_ID);
        expectedBook.setAuthorsList(Set.of(expectedAuthor));
        expectedBook.setGenresList(Set.of(expectedGenre));
        expectedBook.setCommentsList(Set.of(expectedComment));

        when(bookRepository.findById(EXISTING_BOOK_ID)).thenReturn(Optional.of(expectedBook));

        // Call
        val actualBook = bookService.findBookById(expectedBook.getId());

        // Verify
        assertThat(actualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Return expected list books test")
    @Test
    public void findAllBooks_voidInput_shouldReturnExpectedBooksList(){
        // Config
        // create 1 book
        val author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        val genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE);
        val comment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        val expectedBook = new Book(EXISTING_BOOK_TITLE);
        expectedBook.setId(EXISTING_BOOK_ID);
        expectedBook.setAuthorsList(Set.of(author));
        expectedBook.setGenresList(Set.of(genre));
        expectedBook.setCommentsList(Set.of(comment));

        // create 2 book
        val author2 = new Author(EXISTING_AUTHOR_ID_2, EXISTING_AUTHOR_NAME_2);
        val genre2 = new Genre(EXISTING_GENRE_ID_2, EXISTING_GENRE_TYPE_2);
        val comment2 = new Comment(EXISTING_COMMENT_ID_2, EXISTING_COMMENT_TEXT_2);

        val expectedBook2 = new Book(EXISTING_BOOK_TITLE_2);
        expectedBook2.setId(EXISTING_BOOK_ID_2);
        expectedBook2.setAuthorsList(Set.of(author2));
        expectedBook2.setGenresList(Set.of(genre2));
        expectedBook2.setCommentsList(Set.of(comment2));


        List<Book> expectedBookList = List.of(expectedBook, expectedBook2);
        when(bookRepository.findAll()).thenReturn(expectedBookList);

        // Call
        val actualBookList = bookService.findAllBooks();

        // Verify
        assertEquals(actualBookList, expectedBookList);
    }

    @DisplayName("Return expected book by title test")
    @Test
    public void findBookByTitle_validBookTitle_shouldReturnExpectedBookByTitle(){
        // Config
        val author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        val genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE);
        val comment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        val expectedBook = new Book(EXISTING_BOOK_TITLE);
        expectedBook.setId(EXISTING_BOOK_ID);
        expectedBook.setAuthorsList(Set.of(author));
        expectedBook.setGenresList(Set.of(genre));
        expectedBook.setCommentsList(Set.of(comment));

        when(bookRepository.findByTitle(EXISTING_BOOK_TITLE)).thenReturn(List.of(expectedBook));

        // Call
        val actualBook = bookService.findBookByTitle(expectedBook.getTitle());

        // Verify
        assertThat(actualBook).isEqualTo(List.of(expectedBook));
    }

    @DisplayName("Save book test")
    @Test
    public void save_validTitleAndAuthorIdAndGenreIdAndCommentId_shouldSaveBook(){
        // Config
        val author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        val genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE);
        val comment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        val saveBook = new Book(EXPECTED_BOOK_TITLE);
        saveBook.setId(EXPECTED_BOOK_ID);
        saveBook.setAuthorsList(Set.of(author));
        saveBook.setGenresList(Set.of(genre));
        saveBook.setCommentsList(Set.of(comment));

        when(bookRepository.save(any(Book.class))).thenReturn(saveBook);
        when(authorService.findAuthorById(EXISTING_AUTHOR_ID)).thenReturn(Optional.of(author));
        when(genreService.findGenreById(EXISTING_GENRE_ID)).thenReturn(Optional.of(genre));
        when(commentService.findCommentById(EXISTING_COMMENT_ID)).thenReturn(Optional.of(comment));

        // Call
        val actualBook = bookService.saveBook(EXPECTED_BOOK_TITLE, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXISTING_COMMENT_ID);

        // Verify
        assertEquals(saveBook, actualBook);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("Delete book by id test")
    @Test
    public void deleteBookById_validId_shouldCorrectDeleteBookById(){
        // Config
        doNothing().when(bookRepository).deleteById(EXISTING_BOOK_ID);

        // Call
        bookService.deleteBookById(EXISTING_BOOK_ID);

        // Verify
        verify(bookRepository, times(1)).deleteById(EXISTING_BOOK_ID);
    }
}
