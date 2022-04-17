package com.oshovskii.otus.shell.interfaces;

import com.oshovskii.otus.dto.CommentDto;

import java.util.List;

public interface ShellComment {
    long publishCountComments();
    CommentDto publishCommentByID(Long commentId);

    List<CommentDto> publishAllComment();
    CommentDto publishCommentByText(String text);

    CommentDto saveComment(String text);
    String deleteCommentByID(Long commentId);
}
