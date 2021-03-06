package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.CommentDto;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.repositories.interfaces.CommentRepository;
import com.oshovskii.otus.services.interfaces.CommentService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("CommentServiceImpl Test")
@SpringBootTest(classes = CommentServiceImpl.class)
class CommentServiceImplTest {
    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    private static final String EXISTING_COMMENT_ID = "61e9c448ccf1a74f9c05b2f3";
    private static final String EXISTING_COMMENT_TEXT = "Good book";

    @DisplayName("Find comment by text test")
    @Test
    void findCommentByText_validText_shouldReturnExpectedCommentByText() {
        // Config
        val expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        when(commentRepository.findByText(EXISTING_COMMENT_TEXT)).thenReturn(Optional.of(expectedComment));

        // Call
        val actualCommentDto = commentService.findCommentByText(expectedComment.getText());

        // Verify
        assertThat(actualCommentDto).isEqualTo(expectedComment);
    }

    @DisplayName("Save comment test")
    @Test
    void saveComment_validText_shouldSaveComment() {
        // Config
        val savedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        // Call
        val actualCommentDto = commentService.save(savedComment.getText());

        // Verify
        assertEquals(savedComment, actualCommentDto);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }
}