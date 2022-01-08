package com.oshovskii.otus.shell.interfaces;

public interface ConsolePresentation {
    String login(String userName);
    int publishCountBooks();
    String publishBookByID(Long bookId);
    String publishAllBook();
    String insetBook(String title, String author_name, String genre_name);
    String deleteBookByID(Long bookId);
}
