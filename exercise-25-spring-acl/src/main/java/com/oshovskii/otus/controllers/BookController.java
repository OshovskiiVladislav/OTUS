package com.oshovskii.otus.controllers;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.dto.BookToSaveDto;
import com.oshovskii.otus.models.Book;

import java.util.List;

public interface BookController {
    List<BookDto> findAll();
    Book findById(Long id);
    BookDto save(BookToSaveDto bookToSaveDto);
}
