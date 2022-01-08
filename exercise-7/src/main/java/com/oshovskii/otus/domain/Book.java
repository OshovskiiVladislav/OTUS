package com.oshovskii.otus.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Book {
    private Long id;
    private Author author;
    private Genre genre;
    private String title;

    public Book(String title) {
        this.title = title;
    }

}
