package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
    Author findById(Long id);
}