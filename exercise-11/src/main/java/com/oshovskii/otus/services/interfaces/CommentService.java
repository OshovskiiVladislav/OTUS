package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findCommentById(Long id);
    List<Comment> findAllComments();
    Optional<Comment> findCommentByText(String text);
    Comment saveComment(String text);
    long countComments();
    void deleteCommentById(Long id);
}
