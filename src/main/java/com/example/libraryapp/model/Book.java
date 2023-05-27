package com.example.libraryapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "barcode")
    private String barcode;

    @ManyToOne
    private BookDetails bookDetails;

    @OneToOne
    @JoinColumn(name = "loan_id", unique = true)
    private Loan loan;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (isAvailable != book.isAvailable) return false;
        if (!Objects.equals(barcode, book.barcode)) return false;
        if (!Objects.equals(bookDetails, book.bookDetails)) return false;
        return Objects.equals(loan, book.loan);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        result = 31 * result + (bookDetails != null ? bookDetails.hashCode() : 0);
        result = 31 * result + (loan != null ? loan.hashCode() : 0);
        result = 31 * result + (isAvailable ? 1 : 0);
        return result;
    }
}
