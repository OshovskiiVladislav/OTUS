package com.oshovskii.otus.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author authorDanBrown;
    private Author authorScottFitzgerald;
    private Genre genreRoman;
    private Genre genreDetective;
    private Comment commentGood;
    private Comment commentTheBest;

    @ChangeSet(order = "000", id = "dropDB", author = "oshovskii", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "oshovskii", runAlways = true)
    public void initAuthors(MongoTemplate template){
        authorDanBrown = template.save(new Author(null , "Dan Brown", List.of()));
        authorScottFitzgerald = template.save(new Author(null, "Scott Fitzgerald", List.of()));
    }

    @ChangeSet(order = "003", id = "initGenres", author = "oshovskii", runAlways = true)
    public void initGenres(MongoTemplate template){
        genreRoman = template.save(new Genre(null , "Roman", List.of()));
        genreDetective = template.save(new Genre(null, "Detective", List.of()));
    }

    @ChangeSet(order = "004", id = "initComments", author = "oshovskii", runAlways = true)
    public void initComments(MongoTemplate template){
        commentGood = template.save(new Comment(null , "Good book"));
        commentTheBest = template.save(new Comment(null, "The best book"));
    }

    @ChangeSet(order = "005", id = "initBooks", author = "oshovskii", runAlways = true)
    public void initBooks(MongoTemplate template){
        template.save(new Book(
                null,
                "Angels and Demons",
                List.of(authorDanBrown),
                List.of(genreRoman, genreDetective),
                List.of(commentTheBest)));
        template.save(new Book(
                null,
                "The Great Gatsby",
                List.of(authorScottFitzgerald),
                List.of(genreRoman),
                List.of(commentGood)));
    }
}
