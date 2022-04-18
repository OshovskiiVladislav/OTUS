package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Comment;

public interface CommentService {
    Comment findCommentByText(String text);
    Comment save(String text);
}
