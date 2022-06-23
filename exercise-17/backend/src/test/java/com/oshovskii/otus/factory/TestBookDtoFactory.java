package com.oshovskii.otus.factory;

import com.oshovskii.otus.dto.AuthorDto;
import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.dto.GenreDto;
import lombok.val;

import java.util.Set;

public class TestBookDtoFactory {

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

    public static BookDto createBookDtoWithAllInfoById(Long id) {
        if (id == 1L) {
            val authorDto = new AuthorDto(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
            val genreDto = new GenreDto(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE);

            val bookDto = new BookDto();
            bookDto.setId(EXISTING_BOOK_ID);
            bookDto.setTitle(EXISTING_BOOK_TITLE);
            bookDto.setAuthorsList(Set.of(authorDto));
            bookDto.setGenresList(Set.of(genreDto));
            return bookDto;
        } else if (id == 2L) {
            val authorDto2 = new AuthorDto(EXISTING_AUTHOR_ID_2, EXISTING_AUTHOR_NAME_2);
            val genreDto2 = new GenreDto(EXISTING_GENRE_ID_2, EXISTING_GENRE_TYPE_2);

            val bookDto2 = new BookDto();
            bookDto2.setId(EXISTING_BOOK_ID_2);
            bookDto2.setTitle(EXISTING_BOOK_TITLE_2);
            bookDto2.setAuthorsList(Set.of(authorDto2));
            bookDto2.setGenresList(Set.of(genreDto2));
            return bookDto2;
        } else {
            return null;
        }
    }
}
