package com.oshovskii.otus.repositories.interfaces;

import com.oshovskii.otus.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);
    Long count();

    List<Book> findAll();
    Book findByTitle(String title);

    void updateNameById(Long id, String name);
    void deleteById(Long id);
}
