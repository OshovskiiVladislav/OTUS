package com.oshovskii.otus.shell;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.services.interfaces.BookService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.oshovskii.otus.factory.TestBookDtoFactory.createBookDtoWithAllInfoById;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Test ShellBookImpl command")
@SpringBootTest
class ShellBookImplTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private Shell shell;

    private static final String COMMAND_LOGIN = "login";

    private static final String COMMAND_PUBLISH_GET_BOOK_BY_ID = "getBookById 1";
    private static final String COMMAND_PUBLISH_GET_BOOK_BY_TITLE = "findBookByTitle Angels_and_Demons";
    private static final String COMMAND_PUBLISH_ALL_BOOKS = "allBooks";
    private static final String COMMAND_PUBLISH_COUNT_BOOKS = "countBook";
    private static final String COMMAND_PUBLISH_INSERT_BOOK = "saveB 1 1 TestBookTitle";

    private static final String COMMAND_PUBLISH_DELETE_BOOK_BY_ID = "deleteBook 1";
    private static final String COMMAND_PUBLISH_DELETE_BOOK_BY_ID_EXPECTED_MESSAGE = "book with id 1 deleted";

    private static final Long EXPECTED_BOOKS_COUNT = 2L;
    private static final String NEW_BOOK_TITLE = "TestBookTitle";

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "Angels_and_Demons";

    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final Long EXISTING_GENRE_ID = 1L;

    @DisplayName("Should return CommandNotCurrentlyAvailable if the user logged when trying to execute the test command")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void isPublishEventCommandAvailable_inputVoid_shouldReturnCommandNotCurrentlyAvailableObject() {
        // Config and Call
        Object res =  shell.evaluate(() -> COMMAND_PUBLISH_ALL_BOOKS);

        //  Verify
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("Should return all books of the test command execution " +
            "and call service method if the command is executed after logging in")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void publishAllBook_validCommand_shouldReturnExpectedBookList() {
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedBookDto = new BookDto();
        expectedBookDto.setTitle(EXISTING_BOOK_TITLE);

        val expectedBookDto2 = new BookDto();
        expectedBookDto2.setTitle(EXISTING_BOOK_TITLE_2);

        val expectedBookDtoList = List.of(expectedBookDto, expectedBookDto2);

        when(bookService.findAllBooks()).thenReturn(expectedBookDtoList);

        // Call
        val res = (List<BookDto>) shell.evaluate(() -> COMMAND_PUBLISH_ALL_BOOKS);

        // Verify
        assertThat(res).isEqualTo(expectedBookDtoList);
        verify(bookService, times(1)).findAllBooks();
    }

    @DisplayName("Should return count of books and call service method if the command is executed after logging in")
    @Test
    void publishCountBooks_validCommand_shouldReturnBooksCount(){
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
    void publishBookByID_validCommandAndBookId_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedBookDto = new BookDto();
        expectedBookDto.setTitle(EXISTING_BOOK_TITLE);

        when(bookService.findBookById(EXISTING_BOOK_ID)).thenReturn(expectedBookDto);

        // Call
        val actualBook = (BookDto) shell.evaluate(()-> COMMAND_PUBLISH_GET_BOOK_BY_ID);

        // Verify
        assertThat(actualBook).isEqualTo(expectedBookDto);
        verify(bookService, times(1)).findBookById(EXISTING_BOOK_ID);
    }

    @DisplayName("Should return book by title and call service method if the command is executed after logging in")
    @Test
    void publishBookByTitle_validCommandAndBookTitle_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedBookDto = new BookDto();
        expectedBookDto.setTitle(EXISTING_BOOK_TITLE_2);

        when(bookService.findBookByTitle(EXISTING_BOOK_TITLE_2)).thenReturn(expectedBookDto);

        // Call
        val actualBook = (BookDto) shell.evaluate(()-> COMMAND_PUBLISH_GET_BOOK_BY_TITLE);

        // Verify
        assertThat(actualBook).isEqualTo(expectedBookDto);
        verify(bookService, times(1)).findBookByTitle(EXISTING_BOOK_TITLE_2);
    }

    @DisplayName("Save book in db and call service method if the command is executed after logging in")
    @Test
    void saveBook_validCommandAndBook_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);;

        when(bookService.saveBook(NEW_BOOK_TITLE, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID))
                .thenReturn(expectedBookDto);

        // Call
        val actualMessage = (BookDto) shell.evaluate(()-> COMMAND_PUBLISH_INSERT_BOOK);

        // Verify
        assertThat(actualMessage).isEqualTo(expectedBookDto);

        verify(bookService, times(1))
                .saveBook(NEW_BOOK_TITLE, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID);
    }

    @DisplayName("Delete book by id and call service method if the command is executed after logging in")
    @Test
    void deleteByBookId_validCommandAndBookId_shouldReturnExpectedMessage() {
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
