package com.example.libraryapp.repository;

import com.example.libraryapp.model.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryCardRepository extends JpaRepository<LibraryCard, Long> {

    Optional<LibraryCard> findLibraryCardByLibraryCardNumber(int libraryCardNumber);
}
