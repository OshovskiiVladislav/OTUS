package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
