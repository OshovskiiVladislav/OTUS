package com.oshovskii.otus.shell;

import com.oshovskii.otus.dto.CommentDto;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.services.interfaces.CommentService;
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
import static org.assertj.core.api.InstanceOfAssertFactories.map;
import static org.mockito.Mockito.*;

@DisplayName("Test ShellCommentImpl command")
@SpringBootTest
class ShellCommentImplTest {

    @MockBean
    private CommentService commentService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private Shell shell;

    private static final String COMMAND_LOGIN = "login";

    private static final String COMMAND_PUBLISH_SAVE_COMMENT = "saveComment TestCommentText";
    private static final String COMMAND_PUBLISH_SAVE_EXPECTED_MESSAGE = "Save comment <TestCommentText> completed";
    private static final String COMMAND_PUBLISH_ALL_COMMENTS = "allComments";


    private static final String NEW_COMMENT_TEXT = "TestCommentText";

    private static final String EXISTING_COMMENT_ID = "61fad7cb0dac532e3aecb6e7";
    private static final String EXISTING_COMMENT_ID_2 = "61fad7cc0dac532e3aecb6e8";
    private static final String EXISTING_COMMENT_TEXT = "Good book";
    private static final String EXISTING_COMMENT_TEXT_2 = "The best book";

    @DisplayName("Should return all comments of the test command execution " +
            "and call service method if the command is executed after logging in")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void publishAllComments_validCommand_shouldReturnExpectedCommentList() {
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);
        val expectedComment2 = new Comment(EXISTING_COMMENT_ID_2, EXISTING_COMMENT_TEXT_2);

        val expectedCommentDto1 = new CommentDto(expectedComment.getText());
        val expectedCommentDto2 = new CommentDto(expectedComment2.getText());

        val expectedCommentList = List.of(expectedComment, expectedComment2);
        val expectedCommentDtoList = List.of(expectedCommentDto1, expectedCommentDto2);

        when(commentService.findAllComments())
                .thenReturn(expectedCommentList);

        when(modelMapper.map(expectedComment, CommentDto.class))
                .thenReturn(expectedCommentDto1);

        when(modelMapper.map(expectedComment2, CommentDto.class))
                .thenReturn(expectedCommentDto2);

        // Call
        val res = (List<CommentDto>) shell.evaluate(() -> COMMAND_PUBLISH_ALL_COMMENTS);

        // Verify
        assertThat(res).isEqualTo(expectedCommentDtoList);
        verify(commentService, times(1)).findAllComments();
    }

    @DisplayName("Should return CommandNotCurrentlyAvailable if the user logged when trying to execute the test command")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void isPublishEventCommandAvailable_inputVoid_shouldReturnCommandNotCurrentlyAvailableObject() {
        // Config and Call
        Object res = shell.evaluate(() -> COMMAND_PUBLISH_SAVE_COMMENT);

        //  Verify
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("Save comment in db and call service method if the command is executed after logging in")
    @Test
    void saveComment_validCommandAndComment_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedComment = new Comment();
        expectedComment.setText(EXISTING_COMMENT_TEXT);

        when(commentService.save(NEW_COMMENT_TEXT)).thenReturn(expectedComment);

        // Call
        val actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_SAVE_COMMENT);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_SAVE_EXPECTED_MESSAGE);

        verify(commentService, times(1)).save(NEW_COMMENT_TEXT);
    }
}
