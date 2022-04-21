package com.oshovskii.otus.factory;

import com.oshovskii.otus.dto.BookDto;

public class BookDtoFactory {
    public static BookDto createBookDto(int index) {
        return new BookDto(
                (long) index,
                "title_" + index,
                (long) + index,
                (long) + index
        );
    }
}
