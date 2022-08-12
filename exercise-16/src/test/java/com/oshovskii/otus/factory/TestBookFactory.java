package com.oshovskii.otus.factory;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;
import lombok.val;

import java.util.Set;

public class TestBookFactory {

    public static final Long EXISTING_BOOK_ID = 1L;
    public static final Long EXISTING_BOOK_ID_2 = 2L;
    public static final String EXISTING_BOOK_TITLE = "Angels and Demons";
    public static final String EXISTING_BOOK_TITLE_2 = "The Great Gatsby";

    public static final Long EXISTING_AUTHOR_ID = 1L;
    public static final Long EXISTING_AUTHOR_ID_2 = 2L;
    public static final String EXISTING_AUTHOR_NAME = "Dan Brown";
    public static final String EXISTING_AUTHOR_NAME_2 = "Scott Fitzgerald";

    public static final Long EXISTING_GENRE_ID = 1L;
    public static final Long EXISTING_GENRE_ID_2 = 2L;
    public static final String EXISTING_GENRE_TYPE = "Detective";
    public static final String EXISTING_GENRE_TYPE_2 = "Roman";

    public static Book createBookWithAllInfoById(Long id) {
        if (id == 1L) {
            val author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
            val genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE);

            val book = new Book(EXISTING_BOOK_TITLE);
            book.setId(EXISTING_BOOK_ID);
            book.setAuthorsList(Set.of(author));
            book.setGenresList(Set.of(genre));
            return book;
        } else if (id == 2L) {
            val author2 = new Author(EXISTING_AUTHOR_ID_2, EXISTING_AUTHOR_NAME_2);
            val genre2 = new Genre(EXISTING_GENRE_ID_2, EXISTING_GENRE_TYPE_2);

            val book2 = new Book(EXISTING_BOOK_TITLE_2);
            book2.setId(EXISTING_BOOK_ID_2);
            book2.setAuthorsList(Set.of(author2));
            book2.setGenresList(Set.of(genre2));
            return book2;
        } else {
            return null;
        }
    }
}

