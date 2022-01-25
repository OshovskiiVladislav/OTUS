package com.oshovskii.otus.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@ToString
@Setter
@Getter
@Document(collection = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
  //  @Field("id")
    private String id;
    private String title;

    @DBRef
 //   @Field("authors")
    private List<Author> authors;

    @DBRef
  //  @Field("genres")
    private List<Genre> genres;

    @DBRef
  //  @Field("comments")
    private List<Comment> comments;
}
