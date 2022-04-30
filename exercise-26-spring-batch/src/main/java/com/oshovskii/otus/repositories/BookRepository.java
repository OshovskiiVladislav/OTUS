package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.sql.Book;
import java.util.Collection;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
    Set<Book> findByMongoIdIn(@Param("mongoIds") Collection<String> mongoIds);
}