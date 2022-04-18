package com.oshovskii.otus.repositories.interfaces;

import com.oshovskii.otus.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
    Long count();

    List<Comment> findAll();
    Comment findByText(String text);

    void updateTextById(Long id, String text);
    void deleteById(Long id);
}
