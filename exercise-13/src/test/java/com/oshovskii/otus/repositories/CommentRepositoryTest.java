package com.oshovskii.otus.repositories;

import com.oshovskii.otus.repositories.interfaces.CommentRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CommentRepository Test")
@DataMongoTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    private static final String EXISTING_COMMENT_ID = "61e9c448ccf1a74f9c05b2f3";
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