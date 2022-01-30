package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.repositories.BookRepository;
import com.oshovskii.otus.services.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
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
        return modelMapper.map(bookRepository.save(book), BookDto.class);
    }

    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

}
