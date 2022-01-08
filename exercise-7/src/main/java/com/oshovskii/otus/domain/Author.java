package com.oshovskii.otus.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Data
public class Author {
    private Long id;
    private String name;
    private Set<Book> books;


    public Author(String name) {
        this.name = name;
    }
}
