package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.dto.CommentDto;
import com.oshovskii.otus.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDto saveComment(String text);
    CommentDto findByCommentId(Long id);
    Long countComments();

    List<CommentDto> findAllComments();
    CommentDto findByCommentText(String text);

    void updateTextByCommentId(Long id, String text);
    void deleteByCommentId(Long id);

    Optional<Comment> findById(Long id);
}
