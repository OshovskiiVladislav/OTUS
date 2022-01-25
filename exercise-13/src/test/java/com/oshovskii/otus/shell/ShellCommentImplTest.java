package com.oshovskii.otus.shell;

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

    private static final String COMMAND_PUBLISH_SAVE_COMMENT = "saveComment TestCommentText";
    private static final String COMMAND_PUBLISH_SAVE_COMMENT_EXPECTED_MESSAGE = "Save comment <TestCommentText> completed";

    private static final String NEW_COMMENT_TEXT = "TestCommentText";

    private static final String EXISTING_COMMENT_TEXT = "Good book";

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
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_SAVE_COMMENT_EXPECTED_MESSAGE);

        verify(commentService, times(1)).save(NEW_COMMENT_TEXT);
    }
}