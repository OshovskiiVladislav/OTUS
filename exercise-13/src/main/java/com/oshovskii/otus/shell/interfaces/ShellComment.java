package com.oshovskii.otus.shell.interfaces;

import com.oshovskii.otus.dto.CommentDto;

import java.util.List;

public interface ShellComment {
    List<CommentDto> publishAllComment();
    String saveComment(String text);
}
