package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;

import java.util.List;

public interface BookService {
    int countAllBook();
    void insertBook(Book book, Author author, Genre genre);
    Book getBookById(long id);
    List<Book> getAllBook();
    void deleteBookById(long id);
}
