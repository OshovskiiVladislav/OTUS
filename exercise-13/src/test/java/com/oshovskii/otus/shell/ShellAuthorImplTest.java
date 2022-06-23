package com.oshovskii.otus.shell;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.services.interfaces.AuthorService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Test ShellAuthorImpl command")
@SpringBootTest
class ShellAuthorImplTest {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private Shell shell;

    private static final String COMMAND_LOGIN = "login";

    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";
    private static final String NEW_AUTHOR_NAME = "TestAuthor";
    private static final String COMMAND_PUBLISH_SAVE_AUTHOR = "saveAuthor TestAuthor";
    private static final String COMMAND_PUBLISH_SAVE_AUTHOR_EXPECTED_MESSAGE = "Save author <TestAuthor> completed";

    @DisplayName("Should return CommandNotCurrentlyAvailable if the user logged when trying to execute the test command")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void isPublishEventCommandAvailable_inputVoid_shouldReturnCommandNotCurrentlyAvailableObject() {
        // Config and Call
        Object res = shell.evaluate(() -> COMMAND_PUBLISH_SAVE_AUTHOR);

        //  Verify
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("Save comment in db and call service method if the command is executed after logging in")
    @Test
    void saveComment_validCommandAndComment_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedAuthor = new Author();
        expectedAuthor.setName(EXISTING_AUTHOR_NAME);

        when(authorService.save(NEW_AUTHOR_NAME)).thenReturn(expectedAuthor);

        // Call
        val actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_SAVE_AUTHOR);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_SAVE_AUTHOR_EXPECTED_MESSAGE);

        verify(authorService, times(1)).save(NEW_AUTHOR_NAME);
    }
}