package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.GenreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreDto, Long> {
}
