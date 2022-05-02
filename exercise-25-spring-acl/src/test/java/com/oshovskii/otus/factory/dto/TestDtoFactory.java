package com.oshovskii.otus.factory.dto;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;

public class TestDtoFactory {
    public static BookDto createBookDto(int index) {
        return new BookDto(
                (long) index,
                new Author(),
                new Genre(),
                "title_" + index
        );
    }
}
