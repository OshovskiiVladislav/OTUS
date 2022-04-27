package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Book;

import java.util.List;


public interface ConversionService {
    List<BookDto> fromDomain(List<Book> books);
    BookDto fromDomain(Book book);
    Book fromDto(BookDto bookDto);
}
