package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.BookDto;
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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CommentService commentService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public BookDto findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id: "+ id + " not found"));
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList
                .stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDto findBookByTitle(String title) {
        Book book = bookRepository.findBookByTitle(title);
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    @Transactional
    public BookDto saveBook(String title, Long authorId, Long genreId, Long commentId) {
        Comment comment = commentService.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id: " + commentId + " not found"));
        Author author = authorService.findAuthorById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id: " + authorId + " not found"));
        Genre genre = genreService.findGenreById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre with id: " + genreId + " not found"));

        Book book = new Book(title);
        book.setAuthorsList(Set.of(author));
        book.setGenresList(Set.of(genre));
        book.setCommentsList(Set.of(comment));

        return modelMapper.map(bookRepository.save(book), BookDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto findBookByTitleIgnoreCase(String title) {
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Book> example = Example.of(new Book(title),
                caseInsensitiveExampleMatcher);

        Book actual = bookRepository.findOne(example)
                .orElseThrow(() -> new ResourceNotFoundException("Book with title: "+ title + " not found"));
        return modelMapper.map(actual, BookDto.class);
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
