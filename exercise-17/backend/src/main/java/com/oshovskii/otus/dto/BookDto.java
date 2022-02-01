package com.oshovskii.otus.dto;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Comment;
import com.oshovskii.otus.models.Genre;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private Set<Author> authorsList;
    private Set<Genre> genresList;
    private Set<Comment> commentsList;
}
