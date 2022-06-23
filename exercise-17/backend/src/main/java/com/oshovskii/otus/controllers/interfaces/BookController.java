package com.oshovskii.otus.controllers.interfaces;

import com.oshovskii.otus.dto.BookDto;

import java.util.List;

public interface BookController {
    List<BookDto> findAllBooks();
    BookDto findBookById(Long id);
    BookDto saveBook(BookDto bookDto);
    void deleteBook(Long id);
}
