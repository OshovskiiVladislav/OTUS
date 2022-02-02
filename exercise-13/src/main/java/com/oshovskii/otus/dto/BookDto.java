package com.oshovskii.otus.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String title;
    private List<AuthorDto> authorsList;
    private List<GenreDto> genresList;
    private List<CommentDto> commentsList;
}
