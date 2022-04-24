package com.oshovskii.otus.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
@EqualsAndHashCode(of = {"id"})
@Entity
public class GenreDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "type", nullable = false, unique = true)
    private String type;

    public GenreDto(String type) {
        this.type = type;
    }
}
