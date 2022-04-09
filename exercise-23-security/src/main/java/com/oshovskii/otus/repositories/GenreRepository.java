package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
