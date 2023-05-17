package com.example.libraryapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LibraryCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "card_number", nullable = false, unique = true)
    private int libraryCardNumber;

    @OneToMany(mappedBy = "libraryCard")
    private List<Loan> bookLoans = new ArrayList<>();

    @Column(name="is_active")
    private boolean isActive;
}
