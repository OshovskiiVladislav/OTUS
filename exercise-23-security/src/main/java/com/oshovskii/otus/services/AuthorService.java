package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Author;
import java.util.List;


public interface AuthorService {

    Author findById(long id);

    List<Author> findAll();

    Author create(String name);

    void save(long id, String fullName);

    void delete(long id);
}
