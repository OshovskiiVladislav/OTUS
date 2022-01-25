package com.oshovskii.otus.shell;

import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.services.interfaces.GenreService;
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

@DisplayName("Test ShellGenreImpl command")
@SpringBootTest
class ShellGenreImplTest {

    @MockBean
    private GenreService genreService;

    @Autowired
    private Shell shell;

    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_PUBLISH_SAVE_GENRE = "saveGenre TestGenre";
    private static final String COMMAND_PUBLISH_SAVE_GENRE_EXPECTED_MESSAGE = "Save genre <TestGenre> completed";

    private static final String EXISTING_GENRE_TYPE = "TestGenre";
    private static final String NEW_GENRE_TYPE = "TestGenre";


    @DisplayName("Should return CommandNotCurrentlyAvailable if the user logged when trying to execute the test command")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void isPublishEventCommandAvailable_inputVoid_shouldReturnCommandNotCurrentlyAvailableObject() {
        // Config and Call
        Object res = shell.evaluate(() -> COMMAND_PUBLISH_SAVE_GENRE);

        //  Verify
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("Save comment in db and call service method if the command is executed after logging in")
    @Test
    void saveComment_validCommandAndComment_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedGenre = new Genre();
        expectedGenre.setType(EXISTING_GENRE_TYPE);

        when(genreService.save(NEW_GENRE_TYPE)).thenReturn(expectedGenre);

        // Call
        val actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_SAVE_GENRE);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_SAVE_GENRE_EXPECTED_MESSAGE);

        verify(genreService, times(1)).save(NEW_GENRE_TYPE);
    }
}