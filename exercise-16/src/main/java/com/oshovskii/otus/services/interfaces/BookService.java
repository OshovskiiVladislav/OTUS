package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> findAllBooks();
    BookDto findBookById(Long id);
    BookDto saveBook(BookDto bookDto);
    BookDto updateBook(BookDto bookDto);
    void deleteBookById(Long id);
}
