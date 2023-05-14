package com.example.libraryapp.repository;

import com.example.libraryapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM book WHERE isbn=?", nativeQuery = true)
    Optional<Book> findBookByIsbn(String bookIsbn);

    @Query(value = "SELECT * FROM book WHERE barcode=?", nativeQuery = true)
    Optional<Book> findBookByBarcode(Integer barcode);
}
