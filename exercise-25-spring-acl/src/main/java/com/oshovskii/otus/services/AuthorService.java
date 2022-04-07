package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Author;

public interface AuthorService {
    Author findByName(String authorName);
}
