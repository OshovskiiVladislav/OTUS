package com.oshovskii.otus.services.interfaces;

import com.oshovskii.otus.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book saveBook(String title, Long authorId, Long genreId, Long commentId);
    Optional<Book> findByBookId(Long id);
    Long countBooks();

    List<Book> findAllBooks();
    Book findByBookTitle(String title);

    void updateNameByBookId(Long id, String name);
    void deleteByBookId(Long id);
}
