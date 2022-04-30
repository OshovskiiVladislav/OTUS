package com.oshovskii.otus.batch;

import com.oshovskii.otus.models.mongo.MongoAuthor;
import com.oshovskii.otus.models.mongo.MongoBook;
import com.oshovskii.otus.models.mongo.MongoComment;
import com.oshovskii.otus.models.mongo.MongoGenre;
import com.oshovskii.otus.models.sql.Author;
import com.oshovskii.otus.models.sql.Book;
import com.oshovskii.otus.models.sql.Comment;
import com.oshovskii.otus.models.sql.Genre;
import org.springframework.stereotype.Service;

@Service
public class TransformationService {

    public Book transformBook(MongoBook mongoBook) {
        MongoAuthor mongoAuthor = mongoBook.getAuthor();
        MongoGenre mongoGenre = mongoBook.getGenre();

        return new Book(null,
                mongoBook.getName(),
                new Author(null, mongoAuthor.getFirstName(), mongoAuthor.getLastName(), mongoAuthor.getId()),
                new Genre(null, mongoGenre.getName(), mongoGenre.getId()),
                mongoBook.getId());
    }

    public Comment transformComment(MongoComment mongoComment) {
        MongoBook mongoBook = mongoComment.getBook();

        return new Comment(null,
                mongoComment.getText(),
                new Book(null, null, null, null, mongoBook.getId()));
    }
}
