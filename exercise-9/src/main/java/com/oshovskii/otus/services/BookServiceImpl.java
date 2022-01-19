package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.BookDto;
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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public BookDto saveBook(String title, Long authorId, Long genreId, Long commentId) {
        Author author = authorService.findByAuthorId(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id: " +  authorId + " not found"));
        Genre genre = genreService.findByGenreId(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre with id: " +  genreId + " not found"));
        Comment comment = commentService.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id: "+  commentId + " not found"));

        Book book = new Book(title);
        book.setAuthorsList(Set.of(author));
        book.setGenresList(Set.of(genre));
        book.setCommentsList(Set.of(comment));

        return modelMapper.map(bookRepository.save(book), BookDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto findByBookId(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with "+  id + " not found"));
        return modelMapper.map(book, BookDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Long countBooks() {
        return bookRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList
                .stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto findByBookTitle(String title) {
        return modelMapper.map(bookRepository.findByTitle(title), BookDto.class);
    }

    @Transactional
    @Override
    public void updateTitleByBookId(Long id, String title) {
        bookRepository.updateTitleById(id, title);
    }

    @Transactional
    @Override
    public void deleteByBookId(Long id) {
        bookRepository.deleteById(id);
    }
}
