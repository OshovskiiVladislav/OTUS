package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.exceptions.ResourceNotFoundException;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.repositories.AuthorRepository;
import com.oshovskii.otus.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private static final String AUTHOR_NOT_EXIST = "no author found by id";

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }


    @Transactional(readOnly = true)
    @Override
    public Author findById(long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AUTHOR_NOT_EXIST));
    }

    @Transactional
    @Override
    public Author create(String fullName) {
        Author author = new Author(0L, fullName);
        return authorRepository.save(author);
    }


    @Transactional
    @Override
    public void save(long id, String fullName) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            throw new ResourceNotFoundException(AUTHOR_NOT_EXIST);
        }
        author.setName(fullName);
        authorRepository.save(author);
    }



    @Transactional
    @Override
    public void delete(long authorId) {
        authorRepository.findById(authorId).ifPresent(authorRepository::delete);
    }
}
