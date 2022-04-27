package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.exceptions.ResourceNotFoundException;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.repositories.BookRepository;
import com.oshovskii.otus.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    static final String BOOK_NOT_EXIST = "Book with this ID doesn't exist.";

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }


    @Transactional(readOnly = true)
    @Override
    public Book findById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(BOOK_NOT_EXIST));
    }

    @Transactional(readOnly = true)
    @Override
    public Long getBooksCount() {
        return bookRepository.count();
    }

    @Transactional
    @Override
    public void deleteBook(long id) {
        bookRepository.findById(id).ifPresent(bookRepository::delete);
    }


    @Transactional
    @Override
    public Book saveBook(Book book) {
        if (book != null) {
            return bookRepository.save(book);
        } else {
            throw new ResourceNotFoundException(BOOK_NOT_EXIST);
        }
    }
}
