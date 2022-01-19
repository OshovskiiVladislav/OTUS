package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.BookRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
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

    @MockBean
    private ModelMapper modelMapperMock;

    private static final String EXPECTED_BOOK_TITLE = "Test title";
    private static final Long EXPECTED_BOOK_ID = 0L;
    private static final long EXPECTED_BOOKS_COUNT = 2;

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "Angels and Demons";

    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final Long EXISTING_AUTHOR_ID_2 = 2L;
    private static final String EXISTING_AUTHOR_NAME = "Dan Brow";
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

        val expectedBookDto = new BookDto();
        expectedBookDto.setTitle(expectedBook.getTitle());
        expectedBookDto.setAuthorsList(expectedBook.getAuthorsList());
        expectedBookDto.setGenresList(expectedBook.getGenresList());
        expectedBookDto.setCommentsList(expectedBook.getCommentsList());

        when(bookRepository.findById(EXISTING_BOOK_ID)).thenReturn(Optional.of(expectedBook));
        when(modelMapperMock.map(expectedBook, BookDto.class)).thenReturn(expectedBookDto);

        // Call
        val actualBookDto = bookService.findBookById(expectedBook.getId());

        // Verify
        assertThat(actualBookDto).isEqualTo(expectedBookDto);
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

        val expectedBookDto = new BookDto();
        expectedBookDto.setTitle(expectedBook.getTitle());
        expectedBookDto.setAuthorsList(expectedBook.getAuthorsList());
        expectedBookDto.setGenresList(expectedBook.getGenresList());
        expectedBookDto.setCommentsList(expectedBook.getCommentsList());

        // create 2 book
        val author2 = new Author(EXISTING_AUTHOR_ID_2, EXISTING_AUTHOR_NAME_2);
        val genre2 = new Genre(EXISTING_GENRE_ID_2, EXISTING_GENRE_TYPE_2);
        val comment2 = new Comment(EXISTING_COMMENT_ID_2, EXISTING_COMMENT_TEXT_2);

        val expectedBook2 = new Book(EXISTING_BOOK_TITLE_2);
        expectedBook2.setId(EXISTING_BOOK_ID_2);
        expectedBook2.setAuthorsList(Set.of(author2));
        expectedBook2.setGenresList(Set.of(genre2));
        expectedBook2.setCommentsList(Set.of(comment2));

        val expectedBookDto2 = new BookDto();
        expectedBookDto2.setTitle(expectedBook2.getTitle());
        expectedBookDto2.setAuthorsList(expectedBook2.getAuthorsList());
        expectedBookDto2.setGenresList(expectedBook2.getGenresList());
        expectedBookDto2.setCommentsList(expectedBook2.getCommentsList());

        val expectedBookList = List.of(expectedBook, expectedBook2);
        val expectedBookDtoList = List.of(expectedBookDto, expectedBookDto2);
        when(bookRepository.findAll()).thenReturn(expectedBookList);
        when(modelMapperMock.map(expectedBook, BookDto.class)).thenReturn(expectedBookDto);
        when(modelMapperMock.map(expectedBook2, BookDto.class)).thenReturn(expectedBookDto2);

        // Call
        val actualBookDtoList = bookService.findAllBooks();

        // Verify
        assertEquals(actualBookDtoList, expectedBookDtoList);
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

        val expectedBookDto = new BookDto();
        expectedBookDto.setTitle(expectedBook.getTitle());
        expectedBookDto.setAuthorsList(expectedBook.getAuthorsList());
        expectedBookDto.setGenresList(expectedBook.getGenresList());
        expectedBookDto.setCommentsList(expectedBook.getCommentsList());

        when(bookRepository.findBookByTitle(EXISTING_BOOK_TITLE)).thenReturn(expectedBook);
        when(modelMapperMock.map(expectedBook, BookDto.class)).thenReturn(expectedBookDto);

        // Call
        val actualBook = bookService.findBookByTitle(expectedBook.getTitle());

        // Verify
        assertThat(actualBook).isEqualTo(expectedBookDto);
    }

    @DisplayName("Save book test")
    @Test
    public void save_validTitleAndAuthorIdAndGenreIdAndCommentId_shouldSaveBook(){
        // Config
        val author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        val genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE);
        val comment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        val savedBook = new Book(EXPECTED_BOOK_TITLE);
        savedBook.setId(EXPECTED_BOOK_ID);
        savedBook.setAuthorsList(Set.of(author));
        savedBook.setGenresList(Set.of(genre));
        savedBook.setCommentsList(Set.of(comment));

        val expectedBookDto = new BookDto();
        expectedBookDto.setTitle(savedBook.getTitle());
        expectedBookDto.setAuthorsList(savedBook.getAuthorsList());
        expectedBookDto.setGenresList(savedBook.getGenresList());
        expectedBookDto.setCommentsList(savedBook.getCommentsList());

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        when(authorService.findAuthorById(EXISTING_AUTHOR_ID)).thenReturn(Optional.of(author));
        when(genreService.findGenreById(EXISTING_GENRE_ID)).thenReturn(Optional.of(genre));
        when(commentService.findById(EXISTING_COMMENT_ID)).thenReturn(Optional.of(comment));
        when(modelMapperMock.map(savedBook, BookDto.class)).thenReturn(expectedBookDto);

        // Call
        val actualBook = bookService.saveBook(EXPECTED_BOOK_TITLE, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXISTING_COMMENT_ID);

        // Verify
        assertEquals(expectedBookDto, actualBook);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("Return expected number of books test")
    @Test
    public void countBooks_voidInput_shouldReturnExpectedBookCount() {
        // Config
        when(bookRepository.count()).thenReturn(EXPECTED_BOOKS_COUNT);

        // Call
        val actualBookCount = bookService.countBooks();

        // Verify
        assertEquals(EXPECTED_BOOKS_COUNT, actualBookCount);
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
