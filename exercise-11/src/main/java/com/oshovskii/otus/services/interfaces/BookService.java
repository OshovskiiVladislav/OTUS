package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto findBookById(Long id);
    List<BookDto> findAllBooks();
    BookDto findBookByTitle(String title);
    BookDto saveBook(String title, Long authorId, Long genreId, Long commentId);
    long countBooks();
    void deleteBookById(Long id);
}
