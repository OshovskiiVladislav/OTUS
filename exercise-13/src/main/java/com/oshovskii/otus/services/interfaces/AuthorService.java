package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Author;

public interface AuthorService {
    Author findAuthorByName(String name);
    Author save(String name);
}
