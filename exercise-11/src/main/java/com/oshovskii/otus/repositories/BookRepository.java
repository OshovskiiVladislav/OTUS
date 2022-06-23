package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Book;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    List<Book> findAll();

    Book findBookByTitle(String title);

    @Override
    <S extends Book> Optional<S> findOne(Example<S> example);

    long count();

    @Override
    Optional<Book> findById(Long aLong);
}
