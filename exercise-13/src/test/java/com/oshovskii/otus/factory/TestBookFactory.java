package com.oshovskii.otus.factory;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import lombok.val;

import java.util.List;

public class TestBookFactory {

    private static final String EXISTING_BOOK_ID = "61e962590f8ce7592de50e9a";
    private static final String EXISTING_BOOK_ID_2 = "61e9c448ccf1a74f2c05b2f2";

    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "The Great Gatsby";

    private static final String EXISTING_AUTHOR_ID = "61e9c448ccf1a74f9c05b2f6";
    private static final String EXISTING_AUTHOR_ID_2 = "61e9c448ccf1a74f9c05b2f7";
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";
    private static final String EXISTING_AUTHOR_NAME_2 = "Scott Fitzgerald";

    private static final String EXISTING_GENRE_ID = "61e9c448ccf1a74f9c05b2f8";
    private static final String EXISTING_GENRE_ID_2 = "61e9c448ccf1a74f9c05b2f8";
    private static final String EXISTING_GENRE_TYPE = "Detective";
    private static final String EXISTING_GENRE_TYPE_2 = "Roman";

    private static final String EXISTING_COMMENT_ID = "61e9c448ccf1a74f9c05b2f3";
    private static final String EXISTING_COMMENT_ID_2 = "61e9c448ccf1a74f9c05b2f1";
    private static final String EXISTING_COMMENT_TEXT = "Good book";
    private static final String EXISTING_COMMENT_TEXT_2 = "The best book";

    public static Book createBookWithAllInfoByTitle(String title) {
        if (title.equalsIgnoreCase(EXISTING_BOOK_TITLE)) {
            val author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, List.of());
            val genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE, List.of());
            val comment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

            val book = new Book();
            book.setId(EXISTING_BOOK_ID);
            book.setTitle(EXISTING_BOOK_TITLE);
            book.setAuthors(List.of(author));
            book.setGenres(List.of(genre));
            book.setComments(List.of(comment));
            return book;
        } else if (title.equalsIgnoreCase(EXISTING_BOOK_TITLE_2)) {
            val author2 = new Author(EXISTING_AUTHOR_ID_2, EXISTING_AUTHOR_NAME_2, List.of());
            val genre2 = new Genre(EXISTING_GENRE_ID_2, EXISTING_GENRE_TYPE_2, List.of());
            val comment2 = new Comment(EXISTING_COMMENT_ID_2, EXISTING_COMMENT_TEXT_2);

            val book2 = new Book();
            book2.setId(EXISTING_BOOK_ID_2);
            book2.setTitle(EXISTING_BOOK_TITLE_2);
            book2.setAuthors(List.of(author2));
            book2.setGenres(List.of(genre2));
            book2.setComments(List.of(comment2));
            return book2;
        } else {
            return null;
        }
    }
}

