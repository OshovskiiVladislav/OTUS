package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Comment;
import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentRepositoryJpa Test")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
public class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final String EXISTING_COMMENT_TEXT = "Good book";
    private static final Long EXISTING_COMMENT_ID_2 = 2L;

    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final Long EXPECTED_COMMENTS_COUNT = 2L;
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;

    private static final String NEW_COMMENT_TEXT = "Test comment";



    @DisplayName("Should correct save comment test")
    @Test
    void save_validComment_shouldSaveAllCommentInfo() {
        // Config
        val expectedComment = new Comment(null, EXISTING_COMMENT_TEXT);

        // Call and Verify
        commentRepositoryJpa.save(expectedComment);
        assertThat(expectedComment.getId()).isGreaterThan(0);

        val actualComment = em.find(Comment.class, expectedComment.getId());
        assertThat(actualComment).isNotNull().matches(s -> !s.getText().equals(""));
    }

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

    @DisplayName("Should return expected number of comments test")
    @Test
    void count_voidInput_shouldReturnExpectedCommentsCount() {
        // Call
        val actualCountComments = commentRepositoryJpa.count();

        // Verify
        assertThat(actualCountComments).isEqualTo(EXPECTED_COMMENTS_COUNT);
    }

    @DisplayName("Should upload all comments with all information test")
    @Test
    void findAll_voidInput_shouldReturnCorrectBooksListWithAllInfo() {
        // Config
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        // Call and Verify
        System.out.println("\n\n\n\n---------------------------------------------------------------------------------");
        val comments = commentRepositoryJpa.findAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(s -> !s.getText().equals(""));
        System.out.println("---------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("Should upload to correct comment by input text test")
    @Test
    void findByText_validCommentText_shouldFindExpectedCommentByText() {
        // Config
        val expectedComment = em.find(Comment.class, EXISTING_COMMENT_ID);

        // Call
        val actualComment = commentRepositoryJpa.findByText(EXISTING_COMMENT_TEXT);

        // Verify
        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @DisplayName("Should update comment text by id test")
    @Test
    void updateTextById_validCommentIdAndText_shouldUpdateCommentById() {
        // Call
        commentRepositoryJpa.updateTextById(EXISTING_COMMENT_ID_2, NEW_COMMENT_TEXT);

        // Config and Verify
        val expectedBook = em.find(Comment.class, EXISTING_COMMENT_ID_2);
        assertThat(NEW_COMMENT_TEXT).isEqualTo(expectedBook.getText());
    }

    @DisplayName("Should delete comment by id test")
    @Test
    void deleteById_validCommentId_shouldDeleteCommentById() {
        // Config
        val expectedComment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(expectedComment).isNotNull();
        em.detach(expectedComment);

        // Call
        commentRepositoryJpa.deleteById(EXISTING_COMMENT_ID);
        val deletedComment = em.find(Comment.class, EXISTING_COMMENT_ID);

        // Verify
        assertThat(deletedComment).isNull();
    }
}
