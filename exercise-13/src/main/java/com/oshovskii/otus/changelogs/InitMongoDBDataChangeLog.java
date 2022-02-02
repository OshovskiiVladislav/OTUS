package com.oshovskii.otus.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.interfaces.AuthorRepository;
import com.oshovskii.otus.repositories.interfaces.BookRepository;
import com.oshovskii.otus.repositories.interfaces.CommentRepository;
import com.oshovskii.otus.repositories.interfaces.GenreRepository;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author authorDanBrown;
    private Author authorScottFitzgerald;
    private Genre genreRoman;
    private Genre genreDetective;
    private Comment commentGood;
    private Comment commentTheBest;
//    private Book bookAngelsAndDemons;
//    private Book bookTheGreatGatsby;

    @ChangeSet(order = "000", id = "dropDB", author = "oshovskii", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "oshovskii", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository){
        authorDanBrown = authorRepository.save(new Author("61e9c448ccf1a74f9c05b2f6" , "Dan Brown", List.of()));
        authorScottFitzgerald = authorRepository.save(new Author("61e9c448ccf1a74f9c05b2f7", "Scott Fitzgerald", List.of()));
    }

    @ChangeSet(order = "003", id = "initGenres", author = "oshovskii", runAlways = true)
    public void initGenres(GenreRepository genreRepository){
        genreRoman = genreRepository.save(new Genre("61e9c448ccf1a74f9c05b2f3" , "Roman", List.of()));
        genreDetective = genreRepository.save(new Genre("61e9c448ccf1a74f9c05b2f8", "Detective", List.of()));
    }

    @ChangeSet(order = "004", id = "initComments", author = "oshovskii", runAlways = true)
    public void initComments(CommentRepository commentRepository){
        commentGood = commentRepository.save(new Comment(null , "Good book"));
        commentTheBest = commentRepository.save(new Comment(null, "The best book"));
    }

    @ChangeSet(order = "005", id = "initBooks", author = "oshovskii", runAlways = true)
    public void initBooks(BookRepository bookRepository){
         bookRepository.save(new Book(
                null,
                "Angels and Demons",
                List.of(authorDanBrown),
                List.of(genreRoman, genreDetective),
                List.of(commentTheBest)));
         bookRepository.save(new Book(
                null,
                "The Great Gatsby",
                List.of(authorScottFitzgerald),
                List.of(genreRoman),
                List.of(commentGood)));
    }

//    // TODO Правильный ли это способ вставки для связанных сущностей? Как мне организовать подобие ManyToMany (с этим подходом в подарок я получаю stackoverflow)
//    @ChangeSet(order = "006", id = "updateAuthors", author = "oshovskii", runAlways = true)
//    public void updateAuthors(AuthorRepository authorRepository){
//        authorDanBrown = authorRepository.save(new Author("61e9c448ccf1a74f9c05b2f6" , "Dan Brown", List.of(bookAngelsAndDemons)));
//        authorScottFitzgerald = authorRepository.save(new Author("61e9c448ccf1a74f9c05b2f7", "Scott Fitzgerald", List.of(bookTheGreatGatsby)));
//    }
//
//    @ChangeSet(order = "007", id = "updateGenres", author = "oshovskii", runAlways = true)
//    public void updateGenres(GenreRepository genreRepository){
//        genreRoman = genreRepository.save(new Genre("61e9c448ccf1a74f9c05b2f8" , "Roman", List.of(bookAngelsAndDemons)));
//        genreDetective = genreRepository.save(new Genre("61e9c448ccf1a74f9c05b2f8", "Detective", List.of(bookTheGreatGatsby)));
//    }

}
