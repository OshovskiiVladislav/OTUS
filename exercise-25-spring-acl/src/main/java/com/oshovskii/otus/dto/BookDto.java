package com.oshovskii.otus.dto;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private long id;

    private Author author;

    private Genre genre;

    @NotBlank
    @Size(min = 5, max = 100)
    private String title;
}
