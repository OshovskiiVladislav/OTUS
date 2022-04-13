package com.oshovskii.otus.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Setter
@Getter
@Document(collection = "genres")
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    private String id;
    private String type;
}
