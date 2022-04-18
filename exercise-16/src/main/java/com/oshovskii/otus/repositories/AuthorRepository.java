package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Override
    List<Author> findAll();
}
