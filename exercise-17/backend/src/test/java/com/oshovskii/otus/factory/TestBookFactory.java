package com.oshovskii.otus.factory;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import lombok.val;

import java.util.Set;

import static com.oshovskii.otus.utils.Utils.*;

public class TestBookFactory {

    public static Book createBookWithAllInfoById(Long id) {
        if (id == 1L) {
            val author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
            val genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TYPE);
            val comment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_TEXT);

            val book = new Book();
            book.setId(EXISTING_BOOK_ID);
            book.setTitle(EXISTING_BOOK_TITLE);
            book.setAuthorsList(Set.of(author));
            book.setGenresList(Set.of(genre));
            book.setCommentsList(Set.of(comment));
            return book;
        } else if (id == 2L) {
            val author2 = new Author(EXISTING_AUTHOR_ID_2, EXISTING_AUTHOR_NAME_2);
            val genre2 = new Genre(EXISTING_GENRE_ID_2, EXISTING_GENRE_TYPE_2);
            val comment2 = new Comment(EXISTING_COMMENT_ID_2, EXISTING_COMMENT_TEXT_2);

            val book2 = new Book();
            book2.setId(EXISTING_BOOK_ID_2);
            book2.setTitle(EXISTING_BOOK_TITLE_2);
            book2.setAuthorsList(Set.of(author2));
            book2.setGenresList(Set.of(genre2));
            book2.setCommentsList(Set.of(comment2));
            return book2;
        } else {
            return null;
        }
    }
}

