package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> findBookById(Long id);
    List<Book> findAllBooks();
    List<Book> findBookByTitle(String title);
    Book saveBook(String title, Long authorId, Long genreId, Long commentId);
    long countBooks();
    void deleteBookById(Long id);
}
