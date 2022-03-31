package com.oshovskii.otus.shell.interfaces;

import com.oshovskii.otus.dto.BookDto;

import java.util.List;

public interface ShellBook {
    long publishCountBooks();
    BookDto publishBookByID(Long bookId);
    BookDto publishBookByTitleIgnoreCase(String title);

    List<BookDto> publishAllBook();
    BookDto publishBookByTitle(String title);

    BookDto saveBook(Long authorId, Long genreId, String title);
    String deleteBookByID(Long bookId);
}
