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

@DisplayName("BookRepositoryJpa Test ")
@DataJpaTest
@Import(BookRepositoryJpa.class)
public class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final int EXPECTED_NUMBER_OF_BOOKS = 18;

    private static final int EXPECTED_QUERIES_COUNT = 10;

    private static final int EXPECTED_BOOKS_COUNT = 2;
    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "Angels and Demons";

    private static final Long SAVE_BOOK_ID = 3L;
    private static final String SAVE_BOOK_TITLE = "The Da Vinci Code";

    private static final String EXISTING_GENRE_TYPE = "Detective";
    private static final String EXISTING_GENRE_TYPE_2 = "Roman";
    private static final Long EXISTING_GENRE_ID = 1L;
    private static final Long EXISTING_GENRE_ID_2 = 2L;

    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";
    private static final String EXISTING_AUTHOR_NAME_2 = "Dan Brow";
    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final Long EXISTING_AUTHOR_ID_2 = 2L;

    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final String EXISTING_COMMENT_TEXT = "Good book";



    @DisplayName("Should return book by id")
    @Test
    void findById_validBookId_shouldFindExpectedBookById() {
        // Call
        val optionalActualBook = bookRepositoryJpa.findById(EXISTING_BOOK_ID);

        // Verify
        val expectedBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

//    @DisplayName("Should upload all books with all information")
//    @Test
//    void findAll_voidInput_shouldReturnCorrectStudentsListWithAllInfo() {
//        // Config
//        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
//        sessionFactory.getStatistics().setStatisticsEnabled(true);
//
//        // Call and Verify
//        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
//        val books = bookRepositoryJpa.findAll();
//        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
//                .allMatch(s -> !s.getTitle().equals(""))
//                .allMatch(s -> s.getAuthorsList() != null && s.getAuthorsList().size() > 0)
//                .allMatch(s -> s.getGenresList() != null && s.getGenresList().size() > 0)
//                .allMatch(s -> s.getCommentsList() != null && s.getCommentsList().size() > 0);
//        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
//        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
//    }

    @DisplayName("Should correct save book")
    @Test
    void save_validBook_shouldSaveAllStudentInfo() {
        // Config
        val author = new Author(0, EXISTING_AUTHOR_NAME);
        val expectedAuthors = Set.of(author);

        val expectedGenre = new Genre(0, EXISTING_GENRE_TYPE);
        val expectedGenres = Set.of(expectedGenre);

        val expectedComment = new Comment(0, EXISTING_COMMENT_TEXT);
        val expectedComments = Set.of(expectedComment);

        val expectedBook = new Book(0, SAVE_BOOK_TITLE, expectedAuthors, expectedGenres, expectedComments);

        // Call and Verify
        bookRepositoryJpa.save(expectedBook);
        assertThat(expectedBook.getId()).isGreaterThan(0);

        val actualBook = em.find(Book.class, expectedBook.getId());
        assertThat(actualBook).isNotNull().matches(s -> !s.getTitle().equals(""))
                .matches(s -> s.getAuthorsList() != null && s.getAuthorsList().size() > 0)
                .matches(s -> s.getGenresList() != null && s.getGenresList().size() > 0)
                .matches(s -> s.getCommentsList() != null && s.getCommentsList().size() > 0);
    }

    @DisplayName("Should upload to correct book by input name")
    @Test
    void findByTitle_validBookTitle_shouldFindExpectedBookByTitle() {
        // Config
        val expectedBook = em.find(Book.class, EXISTING_BOOK_ID);
        System.out.println(expectedBook.toString());
        // Call
        Book actualBook = bookRepositoryJpa.findByTitle(EXISTING_BOOK_TITLE);

        // Verify
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Should delete book by id")
    @Test
    void shouldDeleteStudentNameById() {
        // Config
        val expectedBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(expectedBook).isNotNull();
        em.detach(expectedBook);

        // Call
        bookRepositoryJpa.deleteById(EXISTING_BOOK_ID);
        val deletedStudent = em.find(Book.class, EXISTING_BOOK_ID);

        // Verify
        assertThat(deletedStudent).isNull();
    }
}