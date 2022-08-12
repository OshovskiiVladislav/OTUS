package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
