package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.interfaces.BookRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static com.oshovskii.otus.factory.TestBookFactory.createBookWithAllInfoByTitle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("BookServiceImpl Test")
@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {

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

    private static final String EXPECTED_BOOK_TITLE = "Test title";

    private static final String EXISTING_BOOK_ID = "61e962590f8ce7592de50e9a";
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "The Great Gatsby";

    private static final String EXISTING_AUTHOR_ID = "61e9c448ccf1a74f2c05b2f3";
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";

    private static final String EXISTING_GENRE_ID = "61e9c448ccf1a74f2c05b2f4";
    private static final String EXISTING_GENRE_TYPE = "Detective";

    private static final String EXISTING_COMMENT_ID = "61e9c448ccf1a74f2c05b2f5";
    private static final String EXISTING_COMMENT_TEXT = "Good book";

    @DisplayName("Return expected book by id test")
    @Test
    void findById_validBookId_shouldReturnExpectedBookById(){
        // Config
        val expectedBook = createBookWithAllInfoByTitle(EXISTING_BOOK_TITLE);

        when(bookRepository.findById(EXISTING_BOOK_ID)).thenReturn(Optional.of(expectedBook));

        // Call
        val actualBookDto = bookService.findById(expectedBook.getId());

        // Verify
        assertThat(actualBookDto).isEqualTo(expectedBook);
    }

    @DisplayName("Return expected list books test")
    @Test
    void findAllBooks_voidInput_shouldReturnExpectedBooksList() {
        // Config
        val expectedBook = createBookWithAllInfoByTitle(EXISTING_BOOK_TITLE);
        val expectedBook2 = createBookWithAllInfoByTitle(EXISTING_BOOK_TITLE_2);

        val expectedBookList = List.of(expectedBook, expectedBook2);

        when(bookRepository.findAll()).thenReturn(expectedBookList);

        // Call
        val actualBookList = bookService.findAll();

        // Verify
        assertEquals(actualBookList, expectedBookList);
    }

    @DisplayName("Return expected book by title test")
    @Test
    void findBookByTitle_validBookTitle_shouldReturnExpectedBookByTitle(){
        // Config
        val expectedBook = createBookWithAllInfoByTitle(EXISTING_BOOK_TITLE);

        when(bookRepository.findByTitle(EXISTING_BOOK_TITLE)).thenReturn(Optional.ofNullable(expectedBook));

        // Call
        val actualBook = bookService.findByTitle(expectedBook.getTitle());

        // Verify
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Save book test")
    @Test
    void save_validTitleAndAuthorIdAndGenreIdAndCommentId_shouldSaveBook(){
        // Config
        val author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        val genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE);
        val comment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        val savedBook = createBookWithAllInfoByTitle(EXISTING_BOOK_TITLE);

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        when(authorService.findAuthorByName(EXISTING_AUTHOR_NAME)).thenReturn(author);
        when(genreService.findGenreByType(EXISTING_GENRE_TYPE)).thenReturn(genre);
        when(commentService.findCommentByText(EXISTING_COMMENT_TEXT)).thenReturn(comment);

        // Call
        val actualBook = bookService.save(
                EXISTING_AUTHOR_NAME,
                EXISTING_GENRE_TYPE,
                EXISTING_COMMENT_TEXT,
                EXPECTED_BOOK_TITLE
        );

        // Verify
        assertEquals(savedBook, actualBook);
        verify(bookRepository, times(1)).save(any(Book.class));
    }
}
