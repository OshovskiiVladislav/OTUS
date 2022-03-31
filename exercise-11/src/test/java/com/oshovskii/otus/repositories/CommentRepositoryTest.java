package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Comment;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentRepository Test")
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final String EXISTING_COMMENT_TEXT = "Good_book";

    @DisplayName("Should return correct comment by input text test")
    @Test
    void findByText_validCommentText_shouldFindExpectedCommentByText() {
        // Config
        val expectedComment = testEntityManager.find(Comment.class ,EXISTING_COMMENT_ID);

        // Call
        val actualComment = commentRepository.findByText(EXISTING_COMMENT_TEXT);

        // Verify
        assertThat(actualComment).isPresent().get().usingRecursiveComparison().isEqualTo(expectedComment);
    }
}
