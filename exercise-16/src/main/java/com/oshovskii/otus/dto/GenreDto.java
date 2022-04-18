package com.oshovskii.otus.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {
    private long id;
    private String type;

    @Override
    public String toString() {
        return type ;
    }
}
