package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.CommentDto;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.repositories.CommentRepositoryJpa;
import com.oshovskii.otus.services.interfaces.CommentService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
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
    private CommentRepositoryJpa commentRepositoryJpa;

    @MockBean
    private ModelMapper modelMapperMock;

    private static final Long EXPECTED_COMMENTS_COUNT = 2L;
    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final Long EXISTING_COMMENT_ID_2 = 2L;
    private static final String EXISTING_COMMENT_TEXT = "Good book";
    private static final String EXISTING_COMMENT_TEXT_2 = "The best book";

    @DisplayName("Save comment test")
    @Test
    public void save_validCommentText_shouldSaveComment() {
        // Config
        val savedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        val expectedCommentDto = new CommentDto();
        expectedCommentDto.setText(savedComment.getText());

        when(commentRepositoryJpa.save(any(Comment.class))).thenReturn(savedComment);
        when(modelMapperMock.map(savedComment, CommentDto.class)).thenReturn(expectedCommentDto);

        // Call
        val actualComment = commentService.saveComment(savedComment.getText());

        // Verify
        assertEquals(expectedCommentDto, actualComment);
        verify(commentRepositoryJpa, times(1)).save(any(Comment.class));
    }

    @DisplayName("Return expected comment by id test")
    @Test
    public void findByCommentId_validCommentId_shouldReturnExpectedCommentById(){
        // Config
        val expectedComment = new Comment(EXISTING_COMMENT_TEXT);
        expectedComment.setId(EXISTING_COMMENT_ID);

        val expectedCommentDto = new CommentDto();
        expectedCommentDto.setText(expectedComment.getText());

        when(commentRepositoryJpa.findById(EXISTING_COMMENT_ID)).thenReturn(Optional.of(expectedComment));
        when(modelMapperMock.map(expectedComment, CommentDto.class)).thenReturn(expectedCommentDto);

        // Call
        val actualCommentDto = commentService.findByCommentId(expectedComment.getId());

        // Verify
        assertThat(actualCommentDto).isEqualTo(expectedCommentDto);
    }

    @DisplayName("Return expected number of comments test")
    @Test
    public void countComments_voidInput_shouldReturnExpectedCommendCount() {
        // Config
        when(commentRepositoryJpa.count()).thenReturn(EXPECTED_COMMENTS_COUNT);

        // Call
        val actualBookCount = commentService.countComments();

        // Verify
        assertEquals(actualBookCount, EXPECTED_COMMENTS_COUNT);
    }

    @DisplayName("Return expected list comments test")
    @Test
    public void findAllComments_voidInput_shouldReturnExpectedCommentsList() {
        // Config
        val expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);
        val expectedComment2 = new Comment(EXISTING_COMMENT_ID_2, EXISTING_COMMENT_TEXT_2);

        val expectedCommentDto = new CommentDto();
        expectedCommentDto.setText(expectedComment.getText());
        val expectedCommentDto2 = new CommentDto();
        expectedCommentDto2.setText(expectedComment2.getText());

        val expectedCommentList = List.of(expectedComment, expectedComment2);
        val expectedCommentDtoList = List.of(expectedCommentDto, expectedCommentDto2);

        when(commentRepositoryJpa.findAll()).thenReturn(expectedCommentList);
        when(modelMapperMock.map(expectedComment, CommentDto.class)).thenReturn(expectedCommentDto);
        when(modelMapperMock.map(expectedComment2, CommentDto.class)).thenReturn(expectedCommentDto2);

        // Call
        val actualCommentDtoList = commentService.findAllComments();

        // Verify
        assertEquals(actualCommentDtoList, expectedCommentDtoList);
    }

    @DisplayName("Return expected comment by text test")
    @Test
    public void findByCommentText_validCommentText_shouldReturnExpectedCommentByText(){
        // Config
        val expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        val expectedCommentDto = new CommentDto();
        expectedCommentDto.setText(expectedComment.getText());

        when(commentRepositoryJpa.findByText(EXISTING_COMMENT_TEXT)).thenReturn(expectedComment);
        when(modelMapperMock.map(expectedComment, CommentDto.class)).thenReturn(expectedCommentDto);

        // Call
        val actualCommentDto = commentService.findByCommentText(expectedComment.getText());

        // Verify
        assertThat(actualCommentDto).isEqualTo(expectedCommentDto);
    }

    @DisplayName("Update comment text by id test")
    @Test
    public void updateTextByCommentId_validCommentIdAndText_shouldUpdateCommentTextById(){
        // Config
        doNothing().when(commentRepositoryJpa).updateTextById(EXISTING_COMMENT_ID_2, EXISTING_COMMENT_TEXT_2);

        // Call
        commentService.updateTextByCommentId(EXISTING_COMMENT_ID_2, EXISTING_COMMENT_TEXT_2);

        // Verify
        verify(commentRepositoryJpa, times(1))
                .updateTextById(EXISTING_COMMENT_ID_2, EXISTING_COMMENT_TEXT_2);
    }

    @DisplayName("Return existing comment by id test")
    @Test
    public void findById_validCommentId_shouldReturnExpectedCommentById(){
        // Config
        val expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        when(commentRepositoryJpa.findById(EXISTING_COMMENT_ID)).thenReturn(Optional.of(expectedComment));

        // Call
        val actualComment = commentService.findById(expectedComment.getId());

        // Verify
        assertThat(actualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("Delete comment by id test")
    @Test
    public void deleteByCommentId_validId_shouldCorrectDeleteCommentById() {
        // Config
        doNothing().when(commentRepositoryJpa).deleteById(EXISTING_COMMENT_ID);

        // Call
        commentService.deleteByCommentId(EXISTING_COMMENT_ID);

        // Verify
        verify(commentRepositoryJpa, times(1)).deleteById(EXISTING_COMMENT_ID);
    }
}
