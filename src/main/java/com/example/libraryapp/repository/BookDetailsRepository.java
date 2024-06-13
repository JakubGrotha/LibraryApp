package com.example.libraryapp.repository;

import com.example.libraryapp.model.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {

    Optional<BookDetails> findBookByIsbn(String bookIsbn);
}
