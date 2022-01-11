package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.repositories.interfaces.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public Long count() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT count(b) FROM Book b", Long.class);
        return query.getSingleResult();
    }

    @Override
    public List<Book> findAll() {
        return entityManager.createQuery(
                "SELECT b FROM Book b "
                + " LEFT JOIN FETCH b.authorsList "
                + " LEFT JOIN FETCH b.genresList "
                + " LEFT JOIN FETCH b.commentsList", Book.class)
                .getResultList();
    }

    @Override
    public Book findByTitle(String title) {
        TypedQuery<Book> query = entityManager.createQuery(
                     "SELECT b FROM Book b "
                        + " LEFT JOIN FETCH b.authorsList "
                        + " LEFT JOIN FETCH b.genresList "
                        + " LEFT JOIN b.commentsList"
                        + " WHERE b.title = :title",
                Book.class);
        query.setParameter("title", title);
        return query.getSingleResult();
    }

    @Override
    public void updateNameById(Long id, String title) {
        Query query = entityManager.createQuery(
                "UPDATE Book b " +
                "SET b.title = :title " +
                "WHERE b.id = :id");
        query.setParameter("title", title);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery("DELETE FROM Book b WHERE b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
