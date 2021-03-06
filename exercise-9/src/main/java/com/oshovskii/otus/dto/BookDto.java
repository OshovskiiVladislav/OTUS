package com.oshovskii.otus.dto;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class BookDto {
    @ToString.Include
    private String title;
    @ToString.Include
    private Set<Author> authorsList;
    @ToString.Include
    private Set<Genre> genresList;
    @ToString.Include
    private Set<Comment> commentsList;
}
