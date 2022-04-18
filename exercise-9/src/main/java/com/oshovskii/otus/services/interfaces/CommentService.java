package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment saveComment(String text);
    Optional<Comment> findByCommentId(Long id);
    Long countComments();

    List<Comment> findAllComments();
    Comment findByCommentText(String text);

    void updateTextByCommentId(Long id, String text);
    void deleteByCommentId(Long id);
}
