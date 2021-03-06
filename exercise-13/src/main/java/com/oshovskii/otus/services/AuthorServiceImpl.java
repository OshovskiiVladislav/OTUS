package com.oshovskii.otus.services;

import com.oshovskii.otus.exceptions.ResourceNotFoundException;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.repositories.interfaces.AuthorRepository;
import com.oshovskii.otus.services.interfaces.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Author findAuthorByName(String name) {
        return authorRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Author with name: "+ name + " not found"));
    }

    @Override
    public Author save(String name) {
        Author author = new Author(null, name, null);
        return authorRepository.save(author);
    }
}
