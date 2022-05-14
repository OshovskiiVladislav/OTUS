package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.BookRepository;
import com.oshovskii.otus.services.interfaces.AuthorService;
import com.oshovskii.otus.services.interfaces.BookService;
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
    private final ModelMapper modelMapper;

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
    @Transactional(readOnly = true)
    public BookDto findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id: "+ id + " not found"));
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    @Transactional
    public BookDto saveBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        Set<Author> authorSet = book.getAuthorsList()
                .stream()
                .map(author -> authorService.findById(author.getId()))
                .collect(Collectors.toSet());
        Set<Genre> genreSet = book.getGenresList()
                .stream()
                .map(genre -> genreService.findById(genre.getId()))
                .collect(Collectors.toSet());
        book.setAuthorsList(authorSet);
        book.setGenresList(genreSet);
        return modelMapper.map(bookRepository.save(book), BookDto.class);
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book with id: " + bookDto.getId() + " not found"));
        book.setTitle(bookDto.getTitle());
        return modelMapper.map(bookRepository.save(book), BookDto.class);
    }

    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
