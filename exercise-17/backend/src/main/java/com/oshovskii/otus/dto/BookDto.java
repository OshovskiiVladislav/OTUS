package com.oshovskii.otus.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDto {
    private Long id;
    private String title;
    private Set<AuthorDto> authorsList;
    private Set<GenreDto> genresList;
}
