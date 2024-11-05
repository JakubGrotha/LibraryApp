package com.example.libraryapp.model;

import com.example.libraryapp.utils.TimeProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private LibraryCard libraryCard;

    @OneToOne(mappedBy = "loan")
    private Book book;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    public static Loan create(LibraryCard libraryCard, Book book, TimeProvider timeProvider, Period loanDuration) {
        LocalDate now = LocalDate.ofInstant(timeProvider.now(), timeProvider.zoneId());
        return Loan.builder()
                .book(book)
                .loanDate(now)
                .returnDate(now.plusMonths(loanDuration.getMonths()))
                .libraryCard(libraryCard)
                .build();
    }
}
