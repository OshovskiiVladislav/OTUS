package com.oshovskii.otus.dto;

import com.oshovskii.otus.models.AuthorDto;
import com.oshovskii.otus.models.GenreDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BookDto {
    private String title;
    private Set<AuthorDto> authorsList;
    private Set<GenreDto> genresList;
}
