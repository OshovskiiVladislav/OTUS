package com.oshovskii.otus.shell.interfaces;

import com.oshovskii.otus.dto.AuthorDto;

import java.util.List;

public interface ShellAuthor {
    List<AuthorDto> publishAllAuthors();
    String saveAuthor(String name);
}
