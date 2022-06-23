package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.repositories.interfaces.CommentRepository;
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
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            entityManager.persist(comment);
            return comment;
        } else {
            return entityManager.merge(comment);
        }
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public Long count() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT count(c) FROM Comment c", Long.class);
        return query.getSingleResult();
    }

    @Override
    public List<Comment> findAll() {
        return entityManager.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
    }

    @Override
    public Comment findByText(String text) {
        TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c WHERE c.text = :text", Comment.class);
        query.setParameter("text", text);
        return query.getSingleResult();
    }

    @Override
    public void updateTextById(Long id, String text) {
        Query query = entityManager.createQuery("UPDATE Comment b SET b.text = :text WHERE b.id = :id");
        query.setParameter("text", text);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery("DELETE FROM Comment c WHERE c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
