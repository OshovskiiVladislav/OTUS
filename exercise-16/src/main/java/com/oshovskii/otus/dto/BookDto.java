package com.oshovskii.otus.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private Set<AuthorDto> authorsList;
    private Set<GenreDto> genresList;
    private Set<CommentDto> commentsList;
}
