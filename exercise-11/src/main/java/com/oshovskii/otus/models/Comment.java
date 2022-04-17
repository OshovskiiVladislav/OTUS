package com.oshovskii.otus.models;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

// TODO вопрос, в каких случаях нам нужно прописать и тут EntityGraph?Или может что-то другое?
//@NamedEntityGraph(
//        name = "Comment.Book.Author.Genre",
//        attributeNodes = {
//                @NamedAttributeNode(value = "book", subgraph = "book-subgraph"),
//        },
//        subgraphs = {
//                @NamedSubgraph(
//                        name = "book-subgraph",
//                        attributeNodes = {
//                                @NamedAttributeNode("authorsList"),
//                                @NamedAttributeNode("genresList")
//                        }
//                )
//        }
//)
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
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
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private Book book;

    public Comment(String text) {
        this.text = text;
    }
}
