package com.oshovskii.otus.shell;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.services.interfaces.BookService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.oshovskii.otus.factory.TestBookDtoFactory.createBookDtoWithAllInfoByTitle;
import static com.oshovskii.otus.factory.TestBookFactory.createBookWithAllInfoByTitle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Test ShellBookImpl command")
@SpringBootTest
class ShellBookImplTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private BookService bookService;

    @Autowired
    private Shell shell;

    private static final String COMMAND_LOGIN = "login";

    private static final String COMMAND_PUBLISH_GET_BOOK_BY_ID = "getBookById 1";
    private static final String COMMAND_PUBLISH_GET_BOOK_BY_TITLE = "findBookByTitle Angels_and_Demons";
    private static final String COMMAND_PUBLISH_ALL_BOOKS = "allBooks";
    private static final String COMMAND_PUBLISH_INSERT_BOOK = "saveB DanBrown TestGenre Goodbook TestBookTitle";

    private static final String COMMAND_PUBLISH_INSERT_BOOK_EXPECTED_MESSAGE = "Save book <TestBookTitle> completed";

    private static final String NEW_BOOK_TITLE = "TestBookTitle";

    private static final String EXISTING_BOOK_ID = "61e962590f8ce7592de50e9a";
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "Angels and Demons";

    private static final String EXISTING_AUTHOR_ID = "61e9c448ccf1a74f9c05b2f6";
    private static final String EXISTING_AUTHOR_NAME = "DanBrown";

    private static final String EXISTING_GENRE_ID = "61e9ce78140334468e63a020";
    private static final String EXISTING_GENRE_TYPE = "TestGenre";

    private static final String EXISTING_COMMENT_ID = "61e9ce6c140334468e63a01f";
    private static final String EXISTING_COMMENT_TEXT = "Goodbook";

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

        val expectedBook = createBookWithAllInfoByTitle(EXISTING_BOOK_TITLE);
        val expectedBook2 = createBookWithAllInfoByTitle(EXISTING_BOOK_TITLE_2);

        val expectedBookList = List.of(expectedBook, expectedBook2);

        when(bookService.findAll()).thenReturn(expectedBookList);

        // Call
        val res = (String) shell.evaluate(() -> COMMAND_PUBLISH_ALL_BOOKS);

        // Verify
        assertThat(res).isEqualTo(expectedBookList.toString());
        verify(bookService, times(1)).findAll();
    }

    @DisplayName("Should return book by id and call service method if the command is executed after logging in")
    @Test
    void publishBookByID_validCommandAndBookId_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedBook = createBookWithAllInfoByTitle(EXISTING_BOOK_TITLE);
        val expectedBookDto = createBookDtoWithAllInfoByTitle(EXISTING_BOOK_TITLE);

        when(bookService.findById(EXISTING_BOOK_ID)).thenReturn(expectedBook);
        when(modelMapper.map(expectedBook, BookDto.class)).thenReturn(expectedBookDto);
        // Call
        val actualBook = (String) shell.evaluate(()-> COMMAND_PUBLISH_GET_BOOK_BY_ID);

        // Verify
        assertThat(actualBook).isEqualTo(expectedBookDto.toString());
        verify(bookService, times(1)).findById(EXISTING_BOOK_ID);
    }

    @DisplayName("Should return book by title and call service method if the command is executed after logging in")
    @Test
    void publishBookByTitle_validCommandAndBookTitle_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedBook = createBookWithAllInfoByTitle(EXISTING_BOOK_TITLE);
        val expectedBookDto = createBookDtoWithAllInfoByTitle(EXISTING_BOOK_TITLE);

        when(bookService.findByTitle(EXISTING_BOOK_TITLE)).thenReturn(expectedBook);
        when(modelMapper.map(expectedBook, BookDto.class)).thenReturn(expectedBookDto);

        // Call
        val actualBook = (String) shell.evaluate(()-> COMMAND_PUBLISH_GET_BOOK_BY_TITLE);

        // Verify
        assertThat(actualBook).isEqualTo(expectedBookDto.toString());
        verify(bookService, times(1)).findByTitle(EXISTING_BOOK_TITLE);
    }

    @DisplayName("Save book in db and call service method if the command is executed after logging in")
    @Test
    void saveBook_validCommandAndBook_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        val expectedBook = createBookWithAllInfoByTitle(EXISTING_BOOK_TITLE);

        when(bookService.save(EXISTING_AUTHOR_NAME, EXISTING_GENRE_TYPE, EXISTING_COMMENT_TEXT, NEW_BOOK_TITLE))
                .thenReturn(expectedBook);

        // Call
        val actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_INSERT_BOOK);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_INSERT_BOOK_EXPECTED_MESSAGE);

        verify(bookService, times(1))
                .save(EXISTING_AUTHOR_NAME, EXISTING_GENRE_TYPE, EXISTING_COMMENT_TEXT, NEW_BOOK_TITLE);
    }
}
