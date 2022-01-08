package com.oshovskii.otus.dao.interfaces;

import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;

import java.util.List;

public interface BookDao {
    int count();
    void insert(Book book, Author author, Genre genre);
    Book getById(Long id);
    List<Book> getAll();
    void deleteById(Long id);
}
