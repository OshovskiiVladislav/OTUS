package com.oshovskii.otus.shell.interfaces;

public interface ShellBook {
    long publishCountBooks();
    String publishBookByID(Long bookId);

    String publishAllBook();
    String publishBookByTitle(String title);

    String saveBook(Long authorId, Long genreId, Long commentId, String title);
    String deleteBookByID(Long bookId);
}
