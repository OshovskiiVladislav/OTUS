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

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @Override
    public Book findById(String bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id: " + bookId + " not found"));
    }

    @Override
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    @Override
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Book with title: " + title + " not found"));
    }

    @Override
    public Book save(String authorName, String genreType, String commentText, String title) {
        Author author = authorService.findAuthorByName(authorName);
        Genre genre = genreService.findGenreByType(genreType);
        Comment comment = commentService.findCommentByText(commentText);

        Book book = new Book(null, title, List.of(author), List.of(genre), List.of(comment));

        return bookRepository.save(book);
    }

//    @Override
//    public List<Author> findBookAuthorsById(String bookId) {
//        return bookRepository.getBookAuthorsById(bookId);
//    }
//
//    @Override
//    public long findAuthorsArrayLengthByBookId(String bookId) {
//        return bookRepository.getAuthorsArrayLengthById(bookId);
//    }
//
//    @Override
//    public void deleteAuthorsArrayElementsById(String bookId) {
//        bookRepository.removeAuthorsArrayElementsById(bookId);
//    }
}
