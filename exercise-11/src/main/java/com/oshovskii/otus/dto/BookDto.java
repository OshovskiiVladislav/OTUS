package com.oshovskii.otus.dto;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
}
