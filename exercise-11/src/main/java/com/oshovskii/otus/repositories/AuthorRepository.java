package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.AuthorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorDto, Long> {
}
