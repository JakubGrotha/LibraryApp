package com.example.libraryapp.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "barcode")
    private Integer barcode;

    @ManyToOne
    private BookDetails bookDetails;

    @OneToOne
    @JoinColumn(name = "loan_id", unique = true)
    private Loan loan;

    @Column(name = "is_available")
    private boolean isAvailable;
}
