package com.oshovskii.otus.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@ToString
@Setter
@Getter
@Document(collection = "authors")
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    private String id;
    private String name;

    @DBRef
    private List<Book> books;
}

