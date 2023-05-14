package com.example.libraryapp.repository;

import com.example.libraryapp.model.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryCardRepository extends JpaRepository<LibraryCard, Long> {
    @Query(value = "SELECT * FROM library_card WHERE card_number=?", nativeQuery = true)
    Optional<LibraryCard> findLibraryCardByCardNumber(int libraryCardNumber);
}
