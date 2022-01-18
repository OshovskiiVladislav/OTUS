package com.oshovskii.otus.shell.interfaces;

public interface ShellComment {
    long publishCountComments();
    String publishCommentByID(Long commentId);

    String publishAllComment();
    String publishCommentByText(String text);

    String saveComment(String text);
    String deleteCommentByID(Long commentId);
}
