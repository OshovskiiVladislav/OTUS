package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
    @EntityGraph(value = "book-author-genre")
    List<Book> findAll();

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @EntityGraph(value = "book-author-genre")
    Book getById(Long id);

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    Book save(@Param("book") Book book);

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    void deleteBookById(Long bookId);
}
