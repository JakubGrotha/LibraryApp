package com.example.libraryapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
