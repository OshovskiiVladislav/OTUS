package com.oshovskii.otus.services;

import com.oshovskii.otus.exceptions.ResourceNotFoundException;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.interfaces.BookRepository;
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
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @Transactional
    @Override
    public Book saveBook(String title, Long authorId, Long genreId, Long commentId) {
        Author author = authorService.findByAuthorId(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author with " +  authorId + " not found"));
        Genre genre = genreService.findByGenreId(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre with " +  genreId + " not found"));
        Comment comment = commentService.findByCommentId(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with "+  commentId + " not found"));

        Book book = new Book(title);
        book.setAuthorsList(Set.of(author));
        book.setGenresList(Set.of(genre));
        book.setCommentsList(Set.of(comment));

        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Optional<Book> findByBookId(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Long countBooks() {
        return bookRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Book findByBookTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Transactional
    @Override
    public void updateNameByBookId(Long id, String name) {
        bookRepository.updateNameById(id, name);
    }

    @Transactional
    @Override
    public void deleteByBookId(Long id) {
        bookRepository.deleteById(id);
    }
}
