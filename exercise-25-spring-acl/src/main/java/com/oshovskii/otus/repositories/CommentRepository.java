package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Override
    List<Comment> findAll();

    @EntityGraph(value = "comment-book-author-genre")
    @Override
    Optional<Comment> findById(Long id);

    List<Comment> findByBookId(Long bookId);

    long countByBookId(long id);
}
