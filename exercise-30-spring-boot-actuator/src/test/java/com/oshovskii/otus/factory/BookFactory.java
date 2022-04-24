package com.oshovskii.otus.factory;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;

public class BookFactory {
    public static Book createBook(int index) {
        return new Book(
                (long) index,
                new Author(),
                new Genre(),
                "title_" + index
        );
    }
}
