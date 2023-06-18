package com.example.libraryapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookDetails that = (BookDetails) o;

        if (id != that.id) return false;
        if (!Objects.equals(isbn, that.isbn)) return false;
        if (!Objects.equals(author, that.author)) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(publisher, that.publisher)) return false;
        if (!Objects.equals(numberOfPages, that.numberOfPages))
            return false;
        if (!Objects.equals(genre, that.genre)) return false;
        if (!Objects.equals(language, that.language)) return false;
        return Objects.equals(books, that.books);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (numberOfPages != null ? numberOfPages.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (books != null ? books.hashCode() : 0);
        return result;
    }
}
