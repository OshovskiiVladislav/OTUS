package com.oshovskii.otus.shell.interfaces;

import com.oshovskii.otus.dto.BookDto;

import java.util.List;

public interface ShellBook {
    BookDto publishBookByID(String bookId);
    List<BookDto> publishAllBook();
    BookDto publishBookByTitle(String title);
//    String publishBookAuthorsById(String bookId);
//    long publishAuthorsArrayLengthByBookId(String bookId);
    String saveBook(String authorName, String genreType, String commentText, String title);
//    String deleteAuthorsArrayElementsByBookId(String bookId);
}
