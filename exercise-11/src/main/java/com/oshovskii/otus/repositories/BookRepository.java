package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Book;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    @EntityGraph(value = "book.author.genre")
    List<Book> findAll();

    @EntityGraph(value = "book.author.genre")
    Book findBookByTitle(String title);

    @Override
    @EntityGraph(value = "book.author.genre")
    <S extends Book> Optional<S> findOne(Example<S> example);

    long count();

    @Override
    @EntityGraph(value = "book.author.genre")
    Optional<Book> findById(Long aLong);
}
