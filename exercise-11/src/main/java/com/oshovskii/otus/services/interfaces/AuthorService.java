package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.AuthorDto;

import java.util.Optional;

public interface AuthorService {
    Optional<AuthorDto> findAuthorById(Long id);
}
