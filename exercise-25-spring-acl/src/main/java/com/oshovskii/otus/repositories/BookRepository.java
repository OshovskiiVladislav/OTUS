package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "book-author-genre")
    List<Book> findAll();

    @EntityGraph(value = "book-author-genre")
    Book getById(Long id);

    Book save(Book book);

    void deleteBookById(Long bookId);
}
