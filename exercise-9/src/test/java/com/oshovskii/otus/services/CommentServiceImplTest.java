package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.repositories.CommentRepositoryJpa;
import com.oshovskii.otus.services.interfaces.CommentService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("CommentServiceImpl Test")
@SpringBootTest(classes = CommentServiceImpl.class)
class CommentServiceImplTest {
    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepositoryJpa commentRepositoryJpa;

    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final String EXISTING_COMMENT_TEXT = "Good book";

    @DisplayName("Return expected comment by id")
    @Test
    public void getCommentById_validCommentId_shouldReturnExpectedCommentById(){
        // Config
        val expectedComment = new Comment(EXISTING_COMMENT_TEXT);
        expectedComment.setId(EXISTING_COMMENT_ID);

        when(commentRepositoryJpa.findById(EXISTING_COMMENT_ID)).thenReturn(Optional.of(expectedComment));

        // Call
        val actualComment = commentService.findByCommentId(expectedComment.getId());

        // Verify
        assertThat(actualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }
}