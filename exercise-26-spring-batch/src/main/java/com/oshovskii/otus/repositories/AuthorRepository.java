package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.sql.Author;
import java.util.Collection;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Set<Author> findByMongoIdIn(@Param("mongoIds") Collection<String> mongoIds);
}
