package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.repositories.BookRepository;
import com.oshovskii.otus.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    public static final String BOOK_NOT_FOUND = "Book not found. Id: %s";

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public Long count() {
        return bookRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(format(BOOK_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            bookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(format(BOOK_NOT_FOUND + e, id));
        }
    }
}
