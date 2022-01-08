package com.oshovskii.otus.shell;

import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;
import com.oshovskii.otus.services.interfaces.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Test shell command")
@SpringBootTest
public class ConsolePresentationImplTest {
    @MockBean
    private BookService bookService;

    @Autowired
    private Shell shell;

    private static final String GREETING_PATTERN = "Добро пожаловать: %s";
    private static final String DEFAULT_LOGIN = "AnyUser";
    private static final String CUSTOM_LOGIN = "John";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_LOGIN_PATTERN = "%s %s";
    private static final String COMMAND_PUBLISH_ALL_BOOKS = "allBooks";
    private static final String COMMAND_PUBLISH_COUNT_BOOKS = "count";
    private static final String COMMAND_PUBLISH_GET_BOOK_BY_ID = "getById 1";
    private static final String COMMAND_PUBLISH_INSERT_BOOK = "ins TestBook TestAuthor TestGenre";
    private static final String COMMAND_PUBLISH_DELETE_BOOK_BY_ID = "deleteBook 1";
    private static final String COMMAND_PUBLISH_DELETE_BOOK_BY_ID_EXPECTED_MESSAGE = "book with id 1 deleted";
    private static final String COMMAND_PUBLISH_INSERT_BOOK_EXPECTED_MESSAGE = "insert book <TestBook> completed";

    private static final int EXISTING_BOOK_ID = 1;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String NEW_BOOK_TITLE = "TestBook";
    private static final String EXISTING_BOOK_TITLE_2 = "Angels and Demons";
    private static final int EXPECTED_BOOKS_COUNT = 2;
    private static final String EXPECTED_AUTHOR_NAME = "TestAuthor";
    private static final String EXPECTED_GENRE_NAME = "TestGenre";

    @DisplayName("Should return greeting pattern for all login")
    @Test
    public void login_validLoginCommand_shouldReturnExpectedGreeting() {
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_LOGIN));
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, CUSTOM_LOGIN));
    }

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
        Book expectedBook = new Book(EXISTING_BOOK_TITLE);
        Book expectedBook2 = new Book(EXISTING_BOOK_TITLE_2);
        List<Book> expectedBookList = List.of(expectedBook, expectedBook2);
        when(bookService.getAllBook()).thenReturn(expectedBookList);

        // Call
        final String res = (String) shell.evaluate(() -> COMMAND_PUBLISH_ALL_BOOKS);

        // Verify
        assertThat(res).isEqualTo(expectedBookList.toString());
        verify(bookService, times(1)).getAllBook();
    }

    @DisplayName("Should return count of books and call service method if the command is executed after logging in")
    @Test
    public void publishCountBooks_validCommand_shouldReturnBooksCount(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        when(bookService.countAllBook()).thenReturn(EXPECTED_BOOKS_COUNT);

        // Call
        final int actualCount = (int) shell.evaluate(()-> COMMAND_PUBLISH_COUNT_BOOKS);

        // Verify
        assertThat(actualCount).isEqualTo(EXPECTED_BOOKS_COUNT);
        verify(bookService, times(1)).countAllBook();

    }

    @DisplayName("Should return book by id and call service method if the command is executed after logging in")
    @Test
    public void publishBookByID_validCommandAndBookId_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        Book expectedBook = new Book(EXISTING_BOOK_TITLE);
        when(bookService.getBookById(EXISTING_BOOK_ID)).thenReturn(expectedBook);

        // Call
        final String actualBook = (String) shell.evaluate(()-> COMMAND_PUBLISH_GET_BOOK_BY_ID);

        // Verify
        assertThat(actualBook).isEqualTo(expectedBook.toString());
        verify(bookService, times(1)).getBookById(EXISTING_BOOK_ID);
    }

    @DisplayName("Insert book in db and call service method if the command is executed after logging in")
    @Test
    public void insetBook_validCommandAndBook_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        Book expectedBook = new Book(NEW_BOOK_TITLE);
        Author expectedAuthor = new Author(EXPECTED_AUTHOR_NAME);
        Genre expectedGenre = new Genre(EXPECTED_GENRE_NAME);
        doNothing().when(bookService).insertBook(expectedBook, expectedAuthor, expectedGenre);

        // Call
        final String actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_INSERT_BOOK);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_INSERT_BOOK_EXPECTED_MESSAGE);
        verify(bookService, times(1)).insertBook(expectedBook, expectedAuthor, expectedGenre);
    }

    @DisplayName("Delete book by id and call service method if the command is executed after logging in")
    @Test
    public void deleteBookByID_validCommandAndBookId_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        doNothing().when(bookService).deleteBookById(EXISTING_BOOK_ID);

        // Call
        final String actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_DELETE_BOOK_BY_ID);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_DELETE_BOOK_BY_ID_EXPECTED_MESSAGE);
        verify(bookService, times(1)).deleteBookById(EXISTING_BOOK_ID);
    }
}
