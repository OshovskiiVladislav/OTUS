package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Book;

import java.util.List;

public interface BookService {
    Long count();
    List<Book> findAll();
    Book findById(Long id);
    Book save(Book book);
    void deleteById(Long id);
}
