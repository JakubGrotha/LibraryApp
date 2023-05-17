package com.example.libraryapp.repository;

import com.example.libraryapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT b FROM Book b WHERE b.isbn=:isbn")
    Optional<Book> findBookByIsbn(@Param("isbn")String bookIsbn);

    @Query(value = "SELECT b FROM Book b WHERE b.barcode=:barcode")
    Optional<Book> findBookByBarcode(@Param("barcode") Integer barcode);
}
