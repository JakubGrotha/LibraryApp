package com.example.libraryapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibraryCard that = (LibraryCard) o;

        if (id != that.id) return false;
        if (libraryCardNumber != that.libraryCardNumber) return false;
        if (isActive != that.isActive) return false;
        if (!Objects.equals(user, that.user)) return false;
        return Objects.equals(bookLoans, that.bookLoans);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + libraryCardNumber;
        result = 31 * result + (bookLoans != null ? bookLoans.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }
}
