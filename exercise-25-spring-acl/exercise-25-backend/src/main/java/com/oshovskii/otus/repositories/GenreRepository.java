package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String genreName);
}
