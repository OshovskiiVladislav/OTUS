package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.repositories.AuthorRepository;
import com.oshovskii.otus.services.interfaces.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }
}
