package com.oshovskii.otus.factory.entity;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;

public class TestEntityFactory {
    public static Book createBook(int index) {
        return new Book(
                (long) index,
                createAuthor(index),
                createGenre(index),
                "book_title_" + index
                );
    }

    public static Author createAuthor(int index) {
        return new Author(
                (long) index,
                "author_name_" + index
        );
    }

    public static Genre createGenre(int index) {
        return new Genre(
                (long) index,
                "genre_name_" + index
        );
    }
}
