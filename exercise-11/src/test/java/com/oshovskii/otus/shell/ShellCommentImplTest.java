package com.oshovskii.otus.shell;

import com.oshovskii.otus.dto.CommentDto;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.services.interfaces.CommentService;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Test ShellCommentImpl command")
@SpringBootTest
class ShellCommentImplTest {
    @MockBean
    private CommentService commentService;

    @Autowired
    private Shell shell;

    private static final String COMMAND_LOGIN = "login";

    private static final String COMMAND_PUBLISH_GET_COMMENT_BY_ID = "getCommentById 1";
    private static final String COMMAND_PUBLISH_ALL_COMMENTS = "allComments";
    private static final String COMMAND_PUBLISH_COUNT_COMMENTS = "countComment";
    private static final String COMMAND_PUBLISH_SAVE_COMMENT = "saveComment TestCommentText";
    private static final String COMMAND_PUBLISH_DELETE_COMMENT_BY_ID = "deleteComment 1";
    private static final String COMMAND_PUBLISH_DELETE_COMMENT_BY_ID_EXPECTED_MESSAGE = "Comment with id 1 deleted";
    private static final String COMMAND_PUBLISH_SAVE_COMMENT_EXPECTED_MESSAGE = "Save comment <TestCommentText> completed";
    private static final String COMMAND_PUBLISH_GET_COMMENT_BY_TEXT = "fct \"The best book\"";

    private static final Long EXPECTED_COMMENTS_COUNT = 2L;
    private static final String NEW_COMMENT_TEXT = "TestCommentText";

    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final Long EXISTING_COMMENT_ID_2 = 2L;
    private static final String EXISTING_COMMENT_TEXT = "Good book";
    private static final String EXISTING_COMMENT_TEXT_2 = "The best book";

    @DisplayName("Should return CommandNotCurrentlyAvailable if the user logged when trying to execute the test command")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void isPublishEventCommandAvailable_inputVoid_shouldReturnCommandNotCurrentlyAvailableObject() {
        // Config and Call
        Object res =  shell.evaluate(() -> COMMAND_PUBLISH_ALL_COMMENTS);

        //  Verify
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("Should return all comments of the test command execution " +
            "and call service method if the command is executed after logging in")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void publishAllComment_validCommand_shouldReturnExpectedCommentList() {
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedCommentDto = new CommentDto();
        expectedCommentDto.setText(EXISTING_COMMENT_TEXT);
        val expectedCommentDto2 = new CommentDto();
        expectedCommentDto2.setText(EXISTING_COMMENT_TEXT_2);

        val expectedCommentDtoList = List.of(expectedCommentDto, expectedCommentDto2);
        when(commentService.findAllComments()).thenReturn(expectedCommentDtoList);

        // Call
        val res = (String) shell.evaluate(() -> COMMAND_PUBLISH_ALL_COMMENTS);

        // Verify
        assertThat(res).isEqualTo(expectedCommentDtoList.toString());
        verify(commentService, times(1)).findAllComments();
    }

    @DisplayName("Should return count of comments and call service method if the command is executed after logging in")
    @Test
    void publishCountComments_validCommand_shouldReturnCommentsCount() {
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        when(commentService.countComments()).thenReturn(EXPECTED_COMMENTS_COUNT);

        // Call
        val actualCount = (Long) shell.evaluate(()-> COMMAND_PUBLISH_COUNT_COMMENTS);

        // Verify
        assertThat(actualCount).isEqualTo(EXPECTED_COMMENTS_COUNT);
        verify(commentService, times(1)).countComments();
    }

    @DisplayName("Should return comment by id and call service method if the command is executed after logging in")
    @Test
    void publishCommentByID_validCommandAndCommentId_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedCommentDto = new CommentDto();
        expectedCommentDto.setText(EXISTING_COMMENT_TEXT);

        when(commentService.findCommentById(EXISTING_COMMENT_ID)).thenReturn(expectedCommentDto);

        // Call
        val actualComment = (String) shell.evaluate(()-> COMMAND_PUBLISH_GET_COMMENT_BY_ID);

        // Verify
        assertThat(actualComment).isEqualTo(expectedCommentDto.toString());
        verify(commentService, times(1)).findCommentById(EXISTING_COMMENT_ID);
    }

    @DisplayName("Save comment in db and call service method if the command is executed after logging in")
    @Test
    public void saveComment_validCommandAndComment_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedCommentDto = new CommentDto();
        expectedCommentDto.setText(EXISTING_COMMENT_TEXT);

        when(commentService.saveComment(NEW_COMMENT_TEXT)).thenReturn(expectedCommentDto);

        // Call
        val actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_SAVE_COMMENT);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_SAVE_COMMENT_EXPECTED_MESSAGE);

        verify(commentService, times(1)).saveComment(NEW_COMMENT_TEXT);
    }

    @DisplayName("Should return comment by text and call service method if the command is executed after logging in")
    @Test
    void publishCommentByText_validCommandAndCommentText_shouldReturnExpectedCommentMessage() {
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        val expectedCommentDto = new CommentDto();
        expectedCommentDto.setText(EXISTING_COMMENT_TEXT);

        when(commentService.findCommentByText(EXISTING_COMMENT_TEXT)).thenReturn(expectedCommentDto);

        // Call
        val actualCommentDtoMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_GET_COMMENT_BY_TEXT);

        // Verify
        assertThat(actualCommentDtoMessage).isEqualTo(expectedCommentDto.toString());
        verify(commentService, times(1)).findCommentByText(EXISTING_COMMENT_TEXT);
    }

    @DisplayName("Delete comment by id and call service method if the command is executed after logging in")
    @Test
    void deleteByCommentId_validCommandAndCommentId_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        doNothing().when(commentService).deleteCommentById(EXISTING_COMMENT_ID);

        // Call
        val actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_DELETE_COMMENT_BY_ID);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_DELETE_COMMENT_BY_ID_EXPECTED_MESSAGE);
        verify(commentService, times(1)).deleteCommentById(EXISTING_COMMENT_ID);
    }
}
