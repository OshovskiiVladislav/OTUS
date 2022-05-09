package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.dto.AuthorDto;
import com.oshovskii.otus.models.Author;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
    Author findById(Long authorId);
}
