package com.oshovskii.otus.shell.interfaces;

public interface ShellComment {
    Long publishCountComments();
    String publishCommentByID(Long commentId);

    String publishAllComment();
    String publishCommentByText(String text);

    String saveComment(String text);
    String deleteCommentByID(Long commentId);
    String updateTextByCommentId(Long id, String text);
}
