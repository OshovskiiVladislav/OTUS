package com.oshovskii.otus.factory;

import com.oshovskii.otus.dto.AuthorDto;
import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.dto.CommentDto;
import com.oshovskii.otus.dto.GenreDto;
import lombok.val;

import java.util.List;

public class TestBookDtoFactory {

    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "The Great Gatsby";

    private static final String EXISTING_AUTHOR_NAME = "Dan Brow";
    private static final String EXISTING_AUTHOR_NAME_2 = "Scott Fitzgerald";

    private static final String EXISTING_GENRE_TYPE = "Detective";
    private static final String EXISTING_GENRE_TYPE_2 = "Roman";

    private static final String EXISTING_COMMENT_TEXT = "Good book";
    private static final String EXISTING_COMMENT_TEXT_2 = "The best book";


    public static BookDto createBookDtoWithAllInfoByTitle(String title) {
        if (title.equalsIgnoreCase(EXISTING_BOOK_TITLE)) {
            val authorDto = new AuthorDto(EXISTING_AUTHOR_NAME);
            val genreDto = new GenreDto(EXISTING_GENRE_TYPE);
            val commentDto = new CommentDto(EXISTING_COMMENT_TEXT);

            val bookDto = new BookDto();
            bookDto.setTitle(EXISTING_BOOK_TITLE);
            bookDto.setAuthorsList(List.of(authorDto));
            bookDto.setGenresList(List.of(genreDto));
            bookDto.setCommentsList(List.of(commentDto));
            return bookDto;
        } else if (title.equalsIgnoreCase(EXISTING_BOOK_TITLE_2)) {
            val authorDto2 = new AuthorDto(EXISTING_AUTHOR_NAME_2);
            val genreDto2 = new GenreDto(EXISTING_GENRE_TYPE_2);
            val commentDto2 = new CommentDto(EXISTING_COMMENT_TEXT_2);

            val bookDto2 = new BookDto();
            bookDto2.setTitle(EXISTING_BOOK_TITLE_2);
            bookDto2.setAuthorsList(List.of(authorDto2));
            bookDto2.setGenresList(List.of(genreDto2));
            bookDto2.setCommentsList(List.of(commentDto2));
            return bookDto2;
        } else {
            return null;
        }
    }
}
