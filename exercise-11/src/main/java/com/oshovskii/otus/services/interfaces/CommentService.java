package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.dto.CommentDto;
import com.oshovskii.otus.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<CommentDto> findAllComments();
    CommentDto findCommentByText(String text);
    CommentDto saveComment(String text);
    long countComments();
    void deleteCommentById(Long id);
    CommentDto findCommentById(Long id);

    Optional<Comment> findById(Long id);
}
