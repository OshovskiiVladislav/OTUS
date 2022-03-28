package com.oshovskii.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookToSaveDto {

    @NotBlank
    @Size(min = 5, max = 100)
    private String title;

    private String author;

    private String genre;
}
