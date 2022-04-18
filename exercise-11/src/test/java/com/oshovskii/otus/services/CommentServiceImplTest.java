package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.CommentRepository;
import com.oshovskii.otus.services.interfaces.CommentService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("CommentServiceImpl Test")
@SpringBootTest(classes = CommentServiceImpl.class)
public class CommentServiceImplTest {
    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final Long EXISTING_COMMENT_ID_2 = 2L;

    private static final String EXISTING_COMMENT_TEXT = "Good book";
    private static final String EXISTING_COMMENT_TEXT_2 = "The best book";

    private static final String EXPECTED_COMMENT_TEXT = "Expected comment";
    private static final Long EXPECTED_COMMENT_ID = 3L;

    @DisplayName("Return expected comment by id")
    @Test
    public void findCommentById_validCommentId_shouldReturnExpectedCommentById(){
        // Config
        val expectedComment = new Comment(EXISTING_COMMENT_TEXT);
        expectedComment.setId(EXISTING_COMMENT_ID);

        when(commentRepository.findById(EXISTING_COMMENT_ID)).thenReturn(Optional.of(expectedComment));

        // Call
        val actualComment = commentService.findCommentById(expectedComment.getId());

        // Verify
        assertThat(actualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("Return expected list comments")
    @Test
    public void findAllComments_voidInput_shouldReturnExpectedCommentsList(){
        // Config
        val expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);
        val expectedComment2 = new Comment(EXISTING_COMMENT_ID_2, EXISTING_COMMENT_TEXT_2);
        val expectedCommentList = List.of(expectedComment, expectedComment2);
        when(commentRepository.findAll()).thenReturn(expectedCommentList);

        // Call
        val actualBookList = commentService.findAllComments();

        // Verify
        assertEquals(actualBookList, expectedCommentList);
    }

    @DisplayName("Find comment by text")
    @Test
    public void findCommentByText_validText_shouldReturnExpectedCommentByText(){
        // Config
        val expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);
        when(commentRepository.findByText(EXISTING_COMMENT_TEXT)).thenReturn(Optional.of(expectedComment));

        // Call
        val actualComment = commentService.findCommentByText(expectedComment.getText());

        // Verify
        assertThat(actualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("Save comment test")
    @Test
    public void saveComment_validText_shouldSaveComment(){
        // Config
        val expectedComment = new Comment(EXPECTED_COMMENT_ID, EXPECTED_COMMENT_TEXT);

        when(commentRepository.save(any(Comment.class))).thenReturn(expectedComment);

        // Call
        val actualComment = commentService.saveComment(expectedComment.getText());

        // Verify
        assertEquals(expectedComment, actualComment);
    }

    @DisplayName("Delete comment by id test")
    @Test
    public void deleteCommentById_validId_shouldCorrectDeleteCommentById(){
        // Config
        doNothing().when(commentRepository).deleteById(EXISTING_COMMENT_ID);

        // Call
        commentService.deleteCommentById(EXISTING_COMMENT_ID);

        // Verify
        verify(commentRepository, times(1)).deleteById(EXISTING_COMMENT_ID);
    }
}
