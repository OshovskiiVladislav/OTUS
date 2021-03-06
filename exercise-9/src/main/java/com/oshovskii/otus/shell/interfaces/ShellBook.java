package com.oshovskii.otus.shell.interfaces;

public interface ShellBook {
    Long publishCountBooks();
    String publishBookByID(Long bookId);

    String publishAllBook();
    String publishBookByTitle(String title);

    String saveBook(Long authorId, Long genreId, Long commentId, String title);
    String deleteBookByID(Long bookId);
    String updateTitleByBookId(Long id, String title);
}
