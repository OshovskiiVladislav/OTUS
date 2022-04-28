package com.oshovskii.otus.models;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
@EqualsAndHashCode(exclude = "book")
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    @ToString.Include
    @Lob
    private String text;

    @ManyToOne(targetEntity = Book.class, fetch = LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private Book book;

    public Comment(String text) {
        this.text = text;
    }
}
