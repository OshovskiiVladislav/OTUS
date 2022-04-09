package com.oshovskii.otus.dto;

import com.oshovskii.otus.models.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

    private long id;

    @NotBlank
    @Size(min = 5, max = 100)
    private String name;

    public AuthorDto(String name) {
        this.name = name;
    }

    public Author toDomainObject() {
        return new Author(this.id, this.name);
    }

    public static AuthorDto fromDomainObject(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
