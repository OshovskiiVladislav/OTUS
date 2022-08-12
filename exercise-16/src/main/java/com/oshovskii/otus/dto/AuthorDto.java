package com.oshovskii.otus.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
