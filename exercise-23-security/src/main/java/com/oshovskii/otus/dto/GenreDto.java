package com.oshovskii.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    private long id;

    @NotBlank
    @Size(min = 5, max = 50)
    private String name;
}
