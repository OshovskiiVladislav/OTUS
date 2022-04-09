package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Book;

import java.util.List;

public interface BookService {

    Book findById(long id);

    List<Book> findAll();

    Long getBooksCount();

    void deleteBook(long id);

    Book saveBook(Book book);
}