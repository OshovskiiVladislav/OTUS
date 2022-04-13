package com.oshovskii.otus.shell;

import com.oshovskii.otus.dto.AuthorDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.services.interfaces.AuthorService;
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
import static org.mockito.Mockito.*;

@DisplayName("Test ShellAuthorImpl command")
@SpringBootTest
class ShellAuthorImplTest {

    @MockBean
    private AuthorService authorService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private Shell shell;

    private static final String COMMAND_LOGIN = "login";

    private static final String EXISTING_AUTHOR_ID = "61e9c448ccf1a74f9c05b2f6";
    private static final String EXISTING_AUTHOR_ID_2 = "61e9c448ccf1a74f9c05b2f7";
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";
    private static final String EXISTING_AUTHOR_NAME_2 = "Dan BrownScott Fitzgerald";
    private static final String NEW_AUTHOR_NAME = "TestAuthor";

    private static final String COMMAND_PUBLISH_SAVE_AUTHOR = "saveAuthor TestAuthor";
    private static final String COMMAND_PUBLISH_SAVE_AUTHOR_EXPECTED_MESSAGE = "Save author <TestAuthor> completed";
    private static final String COMMAND_PUBLISH_ALL_AUTHORS = "allAuthors";


    @DisplayName("Should return all authors of the test command execution " +
            "and call service method if the command is executed after logging in")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void publishAllAuthors_validCommand_shouldReturnExpectedAuthorList() {
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedAuthor = new Author();
        expectedAuthor.setId(EXISTING_AUTHOR_ID);
        expectedAuthor.setName(EXISTING_AUTHOR_NAME);

        val expectedAuthorDto1 = new AuthorDto();
        expectedAuthorDto1.setName(expectedAuthor.getName());

        val expectedAuthor2 = new Author();
        expectedAuthor2.setId(EXISTING_AUTHOR_ID_2);
        expectedAuthor2.setName(EXISTING_AUTHOR_NAME_2);

        val expectedAuthorDto2 = new AuthorDto();
        expectedAuthorDto2.setName(expectedAuthor2.getName());

        val expectedAuthorList = List.of(expectedAuthor, expectedAuthor2);

        val expectedAuthorDtoList = List.of(expectedAuthorDto1, expectedAuthorDto2);

        when(authorService.findAllAuthors())
                .thenReturn(expectedAuthorList);

        when(modelMapper.map(expectedAuthor, AuthorDto.class))
                .thenReturn(expectedAuthorDto1);

        when(modelMapper.map(expectedAuthor2, AuthorDto.class))
                .thenReturn(expectedAuthorDto2);

        // Call
        val res = (List<AuthorDto>) shell.evaluate(() -> COMMAND_PUBLISH_ALL_AUTHORS);

        // Verify
        assertThat(res).isEqualTo(expectedAuthorDtoList);
        verify(authorService, times(1)).findAllAuthors();
    }

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
    void saveComment_validCommandAndComment_shouldReturnExpectedMessage() {
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
