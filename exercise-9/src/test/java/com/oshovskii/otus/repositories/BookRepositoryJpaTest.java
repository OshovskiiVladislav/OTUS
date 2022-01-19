package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepositoryJpa Test")
@DataJpaTest
@Import(BookRepositoryJpa.class)
public class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final int EXPECTED_NUMBER_OF_BOOKS = 2;

    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final int EXPECTED_BOOKS_COUNT = 2;

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";

    private static final String NEW_BOOK_TITLE = "New Book";
    private static final String SAVE_BOOK_TITLE = "The Da Vinci Code";

    private static final String EXISTING_GENRE_TYPE = "Detective";
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";
    private static final String EXISTING_COMMENT_TEXT = "Good book";

    @DisplayName("Should return book by id test")
    @Test
    void findById_validBookId_shouldFindExpectedBookById() {
        // Call
        val optionalActualBook = bookRepositoryJpa.findById(EXISTING_BOOK_ID);

        // Verify
        val expectedBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Should upload all books with all information test")
    @Test
    void findAll_voidInput_shouldReturnCorrectBooksListWithAllInfo() {
        // Config
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        // Call and Verify
        System.out.println("\n\n\n\n---------------------------------------------------------------------------------");
        val books = bookRepositoryJpa.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(s -> !s.getTitle().equals(""))
                .allMatch(s -> s.getAuthorsList() != null && s.getAuthorsList().size() > 0)
                .allMatch(s -> s.getGenresList() != null && s.getGenresList().size() > 0)
                .allMatch(s -> s.getCommentsList() != null && s.getCommentsList().size() > 0);
        System.out.println("---------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("Should correct save book test")
    @Test
    void save_validBook_shouldSaveAllBookInfo() {
        // Config
        val author = new Author(null, EXISTING_AUTHOR_NAME);
        val expectedAuthors = Set.of(author);

        val expectedGenre = new Genre(null, EXISTING_GENRE_TYPE);
        val expectedGenres = Set.of(expectedGenre);

        val expectedComment = new Comment(null, EXISTING_COMMENT_TEXT);
        val expectedComments = Set.of(expectedComment);

        val expectedBook = new Book(null, SAVE_BOOK_TITLE, expectedAuthors, expectedGenres, expectedComments);

        // Call and Verify
        bookRepositoryJpa.save(expectedBook);
        assertThat(expectedBook.getId()).isGreaterThan(0);

        val actualBook = em.find(Book.class, expectedBook.getId());
        assertThat(actualBook).isNotNull().matches(s -> !s.getTitle().equals(""))
                .matches(s -> s.getAuthorsList() != null && s.getAuthorsList().size() > 0)
                .matches(s -> s.getGenresList() != null && s.getGenresList().size() > 0)
                .matches(s -> s.getCommentsList() != null && s.getCommentsList().size() > 0);
    }

    @DisplayName("Should return expected number of books test")
    @Test
    void count_voidInput_shouldReturnExpectedBooksCount() {
        // Call
        val actualCountBooks = bookRepositoryJpa.count();

        // Verify
        assertThat(actualCountBooks).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Should upload to correct book by input name test")
    @Test
    void findByTitle_validBookTitle_shouldFindExpectedBookByTitle() {
        // Config
        val expectedBook = em.find(Book.class, EXISTING_BOOK_ID);

        // Call
        val actualBook = bookRepositoryJpa.findByTitle(EXISTING_BOOK_TITLE);

        // Verify
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Should update book title by id test")
    @Test
    void updateNameById_validBookIdAndTitle_shouldUpdateBookById() {
        // Call
        bookRepositoryJpa.updateTitleById(EXISTING_BOOK_ID_2, NEW_BOOK_TITLE);

        // Config and Verify
        val expectedBook = em.find(Book.class, EXISTING_BOOK_ID_2);
        assertThat(NEW_BOOK_TITLE).isEqualTo(expectedBook.getTitle());
    }

    @DisplayName("Should delete book by id test")
    @Test
    void deleteById_validBookId_shouldDeleteBookById() {
        // Config
        val expectedBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(expectedBook).isNotNull();
        em.detach(expectedBook);

        // Call
        bookRepositoryJpa.deleteById(EXISTING_BOOK_ID);
        val deletedBook = em.find(Book.class, EXISTING_BOOK_ID);

        // Verify
        assertThat(deletedBook).isNull();
    }
}
