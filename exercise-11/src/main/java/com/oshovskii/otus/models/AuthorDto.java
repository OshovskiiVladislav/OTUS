package com.oshovskii.otus.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
@Entity
@EqualsAndHashCode(of = {"id"})
public class AuthorDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    @ToString.Include
    private String name;

    public AuthorDto(String name) {
        this.name = name;
    }
}
