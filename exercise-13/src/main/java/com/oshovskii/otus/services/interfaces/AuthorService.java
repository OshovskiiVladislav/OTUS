package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAllAuthors();
    Author findAuthorByName(String name);
    Author save(String name);
}
