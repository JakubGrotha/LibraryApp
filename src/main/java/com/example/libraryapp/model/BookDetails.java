package com.example.libraryapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@ToString
public class BookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "isbn", nullable = false, columnDefinition = "TEXT")
    private String isbn;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "language", nullable = false)
    private String language;

    @OneToMany(mappedBy = "bookDetails")
    private List<Book> books;

}
