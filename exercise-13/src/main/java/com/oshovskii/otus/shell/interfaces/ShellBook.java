package com.oshovskii.otus.shell.interfaces;

public interface ShellBook {
    String publishBookByID(String bookId);
    String publishAllBook();
    String publishBookByTitle(String title);
    String publishBookAuthorsById(String bookId);
    long publishAuthorsArrayLengthByBookId(String bookId);
    String saveBook(String authorName, String genreType, String commentText, String title);
    String deleteAuthorsArrayElementsByBookId(String bookId);
}
