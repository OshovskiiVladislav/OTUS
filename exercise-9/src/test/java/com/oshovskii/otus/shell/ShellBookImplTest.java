package com.oshovskii.otus.shell;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.services.interfaces.BookService;
import lombok.val;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.internal.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.oshovskii.otus.factory.TestBookDtoFactory.createBookDtoWithAllInfoById;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Test ShellBookImpl command")
@SpringBootTest
public class ShellBookImplTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private Shell shell;

    private static final String COMMAND_LOGIN = "login";

    private static final String COMMAND_PUBLISH_GET_BOOK_BY_ID = "getBookById 1";

    private static final String COMMAND_PUBLISH_ALL_BOOKS = "allBooks";
    private static final String COMMAND_PUBLISH_COUNT_BOOKS = "countBook";
    private static final String COMMAND_PUBLISH_INSERT_BOOK = "saveB 1 1 1 TestBookTitle";
    private static final String COMMAND_PUBLISH_DELETE_BOOK_BY_ID = "deleteBook 1";
    private static final String COMMAND_PUBLISH_DELETE_BOOK_BY_ID_EXPECTED_MESSAGE = "book with id 1 deleted";
    private static final String COMMAND_PUBLISH_INSERT_BOOK_EXPECTED_MESSAGE = "Save book <TestBookTitle> completed";
    private static final String COMMAND_PUBLISH_GET_BOOK_BY_TITLE = "findBookByTitle \"Angels and Demons\"";

    private static final Long EXPECTED_BOOKS_COUNT = 2L;
    private static final String NEW_BOOK_TITLE = "TestBookTitle";

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "Angels and Demons";

    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";

    private static final Long EXISTING_GENRE_ID = 1L;
    private static final String EXISTING_GENRE_TYPE = "Detective";
    private static final String EXPECTED_GENRE_NAME = "TestGenre";

    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final String EXISTING_COMMENT_TEXT = "Good book";

    @DisplayName("Should return CommandNotCurrentlyAvailable if the user logged when trying to execute the test command")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    public void isPublishEventCommandAvailable_inputVoid_shouldReturnCommandNotCurrentlyAvailableObject() {
        // Config and Call
        Object res =  shell.evaluate(() -> COMMAND_PUBLISH_ALL_BOOKS);

        //  Verify
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("Should return all books of the test command execution " +
            "and call service method if the command is executed after logging in")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    public void publishAllBook_validCommand_shouldReturnExpectedBookList() {
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);
        val expectedBookDto2 = createBookDtoWithAllInfoById(EXISTING_BOOK_ID_2);

        val expectedBookDtoList = List.of(expectedBookDto, expectedBookDto2);

        when(bookService.findAllBooks()).thenReturn(expectedBookDtoList);

        // Call
        val res = (String) shell.evaluate(() -> COMMAND_PUBLISH_ALL_BOOKS);

        // Verify
        assertThat(res).isEqualTo(expectedBookDtoList.toString());
        verify(bookService, times(1)).findAllBooks();
    }

    @DisplayName("Should return count of books and call service method if the command is executed after logging in")
    @Test
    public void publishCountBooks_validCommand_shouldReturnBooksCount(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        when(bookService.countBooks()).thenReturn(EXPECTED_BOOKS_COUNT);

        // Call
        val actualCount = (Long) shell.evaluate(()-> COMMAND_PUBLISH_COUNT_BOOKS);

        // Verify
        assertThat(actualCount).isEqualTo(EXPECTED_BOOKS_COUNT);
        verify(bookService, times(1)).countBooks();
    }

    @DisplayName("Should return book by id and call service method if the command is executed after logging in")
    @Test
    public void publishBookByID_validCommandAndBookId_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        when(bookService.findByBookId(EXISTING_BOOK_ID)).thenReturn(expectedBookDto);

        // Call
        val actualBook = (String) shell.evaluate(()-> COMMAND_PUBLISH_GET_BOOK_BY_ID);

        // Verify
        assertThat(actualBook).isEqualTo(expectedBookDto.toString());
        verify(bookService, times(1)).findByBookId(EXISTING_BOOK_ID);
    }

    @DisplayName("Save book in db and call service method if the command is executed after logging in")
    @Test
    public void saveBook_validCommandAndBook_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        val genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE);
        val comment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        val expectedBook = new Book(NEW_BOOK_TITLE);
        expectedBook.setId(EXISTING_BOOK_ID);
        expectedBook.setAuthorsList(Set.of(author));
        expectedBook.setGenresList(Set.of(genre));
        expectedBook.setCommentsList(Set.of(comment));

        val expectedBookDto = new BookDto();
        expectedBookDto.setTitle(expectedBook.getTitle());
        expectedBookDto.setCommentsList(expectedBook.getCommentsList());
        expectedBookDto.setGenresList(expectedBook.getGenresList());
        expectedBookDto.setCommentsList(expectedBook.getCommentsList());


        when(bookService.saveBook(NEW_BOOK_TITLE, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXISTING_COMMENT_ID))
                .thenReturn(expectedBookDto);

        // Call
        val actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_INSERT_BOOK);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_INSERT_BOOK_EXPECTED_MESSAGE);

        verify(bookService, times(1))
                .saveBook(NEW_BOOK_TITLE, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID,EXISTING_COMMENT_ID);
    }

    @DisplayName("Should return book by title and call service method if the command is executed after logging in")
    @Test
    public void publishBookByTitle_validCommandAndBookTitle_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        when(bookService.findByBookTitle(EXISTING_BOOK_TITLE)).thenReturn(expectedBookDto);

        // Call
        val actualBook = (String) shell.evaluate(()-> COMMAND_PUBLISH_GET_BOOK_BY_TITLE);

        // Verify
        assertThat(actualBook).isEqualTo(expectedBookDto.toString());
        verify(bookService, times(1)).findByBookTitle(EXISTING_BOOK_TITLE);
    }

    @DisplayName("Delete book by id and call service method if the command is executed after logging in")
    @Test
    public void deleteByBookId_validCommandAndBookId_shouldReturnExpectedMessage() {
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        doNothing().when(bookService).deleteByBookId(EXISTING_BOOK_ID);

        // Call
        final String actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_DELETE_BOOK_BY_ID);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_DELETE_BOOK_BY_ID_EXPECTED_MESSAGE);
        verify(bookService, times(1)).deleteByBookId(EXISTING_BOOK_ID);
    }
}