package com.oshovskii.otus.shell;

import com.oshovskii.otus.dto.GenreDto;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.services.interfaces.GenreService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.*;

@DisplayName("Test ShellGenreImpl command")
@SpringBootTest
class ShellGenreImplTest {

    @MockBean
    private GenreService genreService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private Shell shell;

    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_PUBLISH_SAVE_GENRE = "saveGenre TestGenre";
    private static final String COMMAND_PUBLISH_SAVE_GENRE_EXPECTED_MESSAGE = "Save genre <TestGenre> completed";
    private static final String COMMAND_PUBLISH_ALL_GENERES = "allGenres";

    private static final String EXISTING_GENRE_ID = "61e9c448ccf1a74f9c05b2f3";
    private static final String EXISTING_GENRE_ID_2 = "61e9c448ccf1a74f9c05b2f8";
    private static final String EXISTING_GENRE_TYPE = "Roman";
    private static final String EXISTING_GENRE_TYPE_2 = "Detective";
    private static final String NEW_GENRE_TYPE = "TestGenre";

    @DisplayName("Should return all genres of the test command execution " +
            "and call service method if the command is executed after logging in")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void publishAllGenres_validCommand_shouldReturnExpectedGenreList() {
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedGenre = new Genre();
        expectedGenre.setId(EXISTING_GENRE_ID);
        expectedGenre.setType(EXISTING_GENRE_TYPE);

        val expectedGenre2 = new Genre();
        expectedGenre2.setId(EXISTING_GENRE_ID_2);
        expectedGenre2.setType(EXISTING_GENRE_TYPE_2);

        val expectedGenreDto1 = new GenreDto();
        expectedGenreDto1.setType(expectedGenre.getType());

        val expectedGenreDto2 = new GenreDto();
        expectedGenreDto2.setType(expectedGenre2.getType());

        val expectedGenreList = List.of(expectedGenre, expectedGenre2);
        val expectedGenreDtoList = List.of(expectedGenreDto1, expectedGenreDto2);

        when(genreService.findAllGenres())
                .thenReturn(expectedGenreList);

        when(modelMapper.map(expectedGenre, GenreDto.class))
                .thenReturn(expectedGenreDto1);

        when(modelMapper.map(expectedGenre2, GenreDto.class))
                .thenReturn(expectedGenreDto2);

        // Call
        val res = (List<GenreDto>) shell.evaluate(() -> COMMAND_PUBLISH_ALL_GENERES);

        // Verify
        assertThat(res).isEqualTo(expectedGenreDtoList);
        verify(genreService, times(1)).findAllGenres();
    }

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
        expectedGenre.setId(EXISTING_GENRE_ID);
        expectedGenre.setType(EXISTING_GENRE_TYPE);

        when(genreService.save(NEW_GENRE_TYPE)).thenReturn(expectedGenre);

        // Call
        val actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_SAVE_GENRE);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_SAVE_GENRE_EXPECTED_MESSAGE);

        verify(genreService, times(1)).save(NEW_GENRE_TYPE);
    }
}
