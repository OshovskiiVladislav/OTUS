package com.oshovskii.otus.dto;

import lombok.*;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CommentDto {
    @ToString.Include
    private String text;
}
