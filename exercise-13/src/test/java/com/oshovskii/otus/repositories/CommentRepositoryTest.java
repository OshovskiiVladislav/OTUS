package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.repositories.interfaces.CommentRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentRepository Test")
@EnableConfigurationProperties
@DataMongoTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    private static final String SAVED_COMMENT_TEXT = "Test note";

    @DisplayName("Should return correct comment by input text test")
    @Test
    void findByText_validCommentText_shouldFindExpectedCommentByText() {
        // Config
        val savedComment = commentRepository.save(new Comment(null, SAVED_COMMENT_TEXT));

        // Call
        val actualComment = commentRepository.findByText(SAVED_COMMENT_TEXT);

        // Verify
        assertThat(actualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(savedComment);
    }
}