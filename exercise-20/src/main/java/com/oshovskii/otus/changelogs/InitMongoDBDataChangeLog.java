package com.oshovskii.otus.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import org.bson.types.ObjectId;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "init", author = "pk", runAlways = true)
    public void init(MongockTemplate template) {
        Author author1 = new Author(ObjectId.get().toString(), "Author_first_name_1", "Author_L1");
        Author author2 = new Author(ObjectId.get().toString(), "Author_first_name_2", "Author_L2");

        Genre genre1 = new Genre(ObjectId.get().toString(), "Genre_1");
        Genre genre2 = new Genre(ObjectId.get().toString(), "Genre_2");

        Book book1 = template.save(new Book(null, "Book_1", author1, genre1));
        Book book2 = template.save(new Book(null, "Book_2", author2, genre2));
        template.save(new Book(null, "Book_3", author1, genre1));

        template.insertAll(List.of(new Comment(null, "Good book", book1),
                new Comment(null, "The best book", book2),
                new Comment(null, "Amazing book", book1)));
    }
}
