package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAllComments();
    Comment findCommentByText(String text);
    Comment save(String text);
}
