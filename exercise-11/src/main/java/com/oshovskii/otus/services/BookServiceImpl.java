package com.oshovskii.otus.services;

import com.oshovskii.otus.exceptions.ResourceNotFoundException;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.BookRepository;
import com.oshovskii.otus.services.interfaces.AuthorService;
import com.oshovskii.otus.services.interfaces.BookService;
import com.oshovskii.otus.services.interfaces.CommentService;
import com.oshovskii.otus.services.interfaces.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CommentService commentService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Transactional(readOnly = true)
    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Book> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        System.out.println(books);      // БОРЕЦ С LazyInitializationException
        return books;
    }

    @Transactional
    public List<Book> findBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Transactional
    public Book saveBook(String title, Long authorId, Long genreId, Long commentId) {
        Comment comment = commentService.findCommentById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with "+  commentId + " not found"));
        Author author = authorService.findAuthorById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author with " +  authorId + " not found"));
        Genre genre = genreService.findGenreById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre with " +  genreId + " not found"));

        Book book = new Book(title);
        book.setAuthorsList(Set.of(author));
        book.setGenresList(Set.of(genre));
        book.setCommentsList(Set.of(comment));

        return bookRepository.save(book);
    }

    @Override
    public long countBooks() {
        return bookRepository.count();
    }

    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
