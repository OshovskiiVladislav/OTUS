package com.oshovskii.otus.factory;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Genre;
import lombok.val;

import java.util.Set;

public class TestBookDtoFactory {

    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "Angels and Demons";

    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final Long EXISTING_AUTHOR_ID_2 = 2L;
    private static final String EXISTING_AUTHOR_NAME = "Dan Brow";
    private static final String EXISTING_AUTHOR_NAME_2 = "Dan Brown";

    private static final Long EXISTING_GENRE_ID = 1L;
    private static final Long EXISTING_GENRE_ID_2 = 2L;
    private static final String EXISTING_GENRE_TYPE = "Detective";
    private static final String EXISTING_GENRE_TYPE_2 = "Roman";

    public static BookDto createBookDtoWithAllInfoById(Long id) {
        if (id == 1L) {
            val author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
            val genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE);

            val bookDto = new BookDto();
            bookDto.setTitle(EXISTING_BOOK_TITLE);
            bookDto.setAuthorsList(Set.of(author));
            bookDto.setGenresList(Set.of(genre));
            return bookDto;
        } else if (id == 2L) {
            val author2 = new Author(EXISTING_AUTHOR_ID_2, EXISTING_AUTHOR_NAME_2);
            val genre2 = new Genre(EXISTING_GENRE_ID_2, EXISTING_GENRE_TYPE_2);

            val bookDto2 = new BookDto();
            bookDto2.setTitle(EXISTING_BOOK_TITLE_2);
            bookDto2.setAuthorsList(Set.of(author2));
            bookDto2.setGenresList(Set.of(genre2));
            return bookDto2;
        } else {
            return null;
        }
    }
}
