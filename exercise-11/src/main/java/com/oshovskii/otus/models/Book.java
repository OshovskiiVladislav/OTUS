package com.oshovskii.otus.models;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
@NamedEntityGraph(name = "book.author.genre",
        attributeNodes = {@NamedAttributeNode("authorsList"),
                          @NamedAttributeNode("genresList")})
@EqualsAndHashCode(of = {"id"})
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ToString.Include
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ToString.Exclude
    @ManyToMany(targetEntity = AuthorDto.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<AuthorDto> authorsList;

    @ToString.Exclude
    @ManyToMany(targetEntity = GenreDto.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<GenreDto> genresList;

    public Book(String title) {
        this.title = title;
    }
}
