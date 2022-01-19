package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.CommentDto;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.repositories.CommentRepository;
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
import static org.mockito.Mockito.*;

@DisplayName("CommentServiceImpl Test")
@SpringBootTest(classes = CommentServiceImpl.class)
public class CommentServiceImplTest {
    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private ModelMapper modelMapperMock;

    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final Long EXISTING_COMMENT_ID_2 = 2L;

    private static final String EXISTING_COMMENT_TEXT = "Good book";
    private static final String EXISTING_COMMENT_TEXT_2 = "The best book";

    private static final long EXPECTED_COMMENTS_COUNT = 2;

    @DisplayName("Return expected comment by id test")
    @Test
    public void findCommentById_validCommentId_shouldReturnExpectedCommentById() {
        // Config
        val expectedComment = new Comment(EXISTING_COMMENT_TEXT);
        expectedComment.setId(EXISTING_COMMENT_ID);

        val expectedCommentDto = new CommentDto();
        expectedCommentDto.setText(expectedComment.getText());

        when(commentRepository.findById(EXISTING_COMMENT_ID)).thenReturn(Optional.of(expectedComment));
        when(modelMapperMock.map(expectedComment, CommentDto.class)).thenReturn(expectedCommentDto);

        // Call
        val actualCommentDto = commentService.findCommentById(expectedComment.getId());

        // Verify
        assertThat(actualCommentDto).isEqualTo(expectedCommentDto);
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

        when(commentRepository.findAll()).thenReturn(expectedCommentList);
        when(modelMapperMock.map(expectedComment, CommentDto.class)).thenReturn(expectedCommentDto);
        when(modelMapperMock.map(expectedComment2, CommentDto.class)).thenReturn(expectedCommentDto2);

        // Call
        val actualBookListDto = commentService.findAllComments();

        // Verify
        assertEquals(actualBookListDto, expectedCommentDtoList);
    }

    @DisplayName("Find comment by text test")
    @Test
    public void findCommentByText_validText_shouldReturnExpectedCommentByText() {
        // Config
        val expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        val expectedCommentDto = new CommentDto();
        expectedCommentDto.setText(expectedComment.getText());

        when(commentRepository.findByText(EXISTING_COMMENT_TEXT)).thenReturn(Optional.of(expectedComment));
        when(modelMapperMock.map(expectedComment, CommentDto.class)).thenReturn(expectedCommentDto);

        // Call
        val actualCommentDto = commentService.findCommentByText(expectedComment.getText());

        // Verify
        assertThat(actualCommentDto).isEqualTo(expectedCommentDto);
    }

    @DisplayName("Save comment test")
    @Test
    public void saveComment_validText_shouldSaveComment() {
        // Config
        val savedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        val expectedCommentDto = new CommentDto();
        expectedCommentDto.setText(savedComment.getText());

        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);
        when(modelMapperMock.map(savedComment, CommentDto.class)).thenReturn(expectedCommentDto);

        // Call
        val actualCommentDto = commentService.saveComment(savedComment.getText());

        // Verify
        assertEquals(expectedCommentDto, actualCommentDto);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @DisplayName("Delete comment by id test")
    @Test
    public void deleteCommentById_validId_shouldCorrectDeleteCommentById() {
        // Config
        doNothing().when(commentRepository).deleteById(EXISTING_COMMENT_ID);

        // Call
        commentService.deleteCommentById(EXISTING_COMMENT_ID);

        // Verify
        verify(commentRepository, times(1)).deleteById(EXISTING_COMMENT_ID);
    }

    @DisplayName("Return existing comment by id test")
    @Test
    public void findById_validCommentId_shouldReturnExpectedCommentById(){
        // Config
        val expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

        when(commentRepository.findById(EXISTING_COMMENT_ID)).thenReturn(Optional.of(expectedComment));

        // Call
        val actualComment = commentService.findById(expectedComment.getId());

        // Verify
        assertThat(actualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("Return expected number of comments test")
    @Test
    public void countComments_voidInput_shouldReturnExpectedCommendCount() {
        // Config
        when(commentRepository.count()).thenReturn(EXPECTED_COMMENTS_COUNT);

        // Call
        val actualBookCount = commentService.countComments();

        // Verify
        assertEquals(EXPECTED_COMMENTS_COUNT, actualBookCount);
    }
}
