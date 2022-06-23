package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookDto saveBook(String title, Long authorId, Long genreId, Long commentId);
    BookDto findByBookId(Long id);
    Long countBooks();

    List<BookDto> findAllBooks();
    BookDto findByBookTitle(String title);

    void updateTitleByBookId(Long id, String title);
    void deleteByBookId(Long id);
}
