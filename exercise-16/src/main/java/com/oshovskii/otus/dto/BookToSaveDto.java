package com.oshovskii.otus.dto;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Genre;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookToSaveDto {
    private Long id;
    private String title;
    private Author author;
    private Genre genre;
}
