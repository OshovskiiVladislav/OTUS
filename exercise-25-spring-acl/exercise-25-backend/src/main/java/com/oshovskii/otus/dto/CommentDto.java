package com.oshovskii.otus.dto;

import com.oshovskii.otus.models.Book;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(exclude = "book")
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;

    @ToString.Exclude
    private Book book;

    @NotBlank
    @Size(min = 5, max = 100)
    private String note;
}
