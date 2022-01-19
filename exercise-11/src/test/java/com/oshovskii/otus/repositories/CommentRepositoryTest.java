package com.oshovskii.otus.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentRepository Test")
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final String EXISTING_COMMENT_TEXT = "Good book";

    @DisplayName("Should return correct comment by input text test")
    @Test
    void findByText_validCommentText_shouldFindExpectedCommentByText() {
        // Config
        val expectedComment = commentRepository.findById(EXISTING_COMMENT_ID);
        // Call
        val actualComment = commentRepository.findByText(EXISTING_COMMENT_TEXT);

        // Verify
        assertThat(actualComment).isEqualTo(expectedComment);
    }
}
