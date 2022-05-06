package com.oshovskii.otus.controllers;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.dto.BookToSaveDto;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.services.implementations.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookControllerImpl {
    private final BookServiceImpl bookServiceImpl;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<BookDto> findAll() {
        return bookServiceImpl.findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable("id") Long id) {
        Book book = bookServiceImpl.findById(id);
        return modelMapper.map(book, BookDto.class);
    }

    @PostMapping
    public BookDto save(BookToSaveDto bookToSaveDto) {
        Book book = bookServiceImpl.save(bookToSaveDto);
        return modelMapper.map(book, BookDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
         bookServiceImpl.deleteById(id);
    }
}
