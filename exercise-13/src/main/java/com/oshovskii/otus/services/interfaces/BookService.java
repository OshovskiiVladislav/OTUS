package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;

import java.util.List;

public interface BookService {
    Book findById(String bookId);
    List<Book> findAll();
    Book findByTitle(String title);
    Book save(String authorName, String genreType, String commentText, String title);
    List<Author> findBookAuthorsById(String bookId);
    long findAuthorsArrayLengthByBookId(String bookId);
    void deleteAuthorsArrayElementsById(String bookId);
}
