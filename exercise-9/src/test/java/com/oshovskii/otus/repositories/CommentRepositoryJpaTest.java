package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Comment;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentRepositoryJpa Test ")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
public class CommentRepositoryJpaTest {
    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final Long EXISTING_COMMENT_ID = 1L;

    @DisplayName("Should return comment by id")
    @Test
    void findById_validCommentId_shouldFindExpectedCommentById() {
        // Call
        val optionalActualComment = commentRepositoryJpa.findById(EXISTING_COMMENT_ID);

        // Verify
        val expectedComment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(optionalActualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }
}