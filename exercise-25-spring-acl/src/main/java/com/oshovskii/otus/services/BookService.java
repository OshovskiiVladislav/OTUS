package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.BookToSaveDto;
import com.oshovskii.otus.models.Book;

import java.util.List;

public interface BookService {
    Book save(BookToSaveDto bookToSaveDto);
    List<Book> findAll();
    Book findById(Long bookId);
    void deleteById(Long bookId);
}
