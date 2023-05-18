package com.example.libraryapp.repository;

import com.example.libraryapp.model.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {
    @Query(value = "SELECT b FROM BookDetails b WHERE b.isbn=:isbn")
    Optional<BookDetails> findBookByIsbn(@Param("isbn")String bookIsbn);
}
