package com.oshovskii.otus.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;
    private String text;

    @Override
    public String toString() {
        return text;
    }
}
