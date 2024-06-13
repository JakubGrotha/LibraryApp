package com.example.libraryapp.repository;

import com.example.libraryapp.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByBarcode(@Param("barcode") String barcode);

    @Query("SELECT b FROM Book b WHERE LOWER(b.bookDetails.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<Book> findAllByBookDetailsTitle(Pageable pageable, @Param("title") String title);


    @Query("SELECT b FROM Book b WHERE LOWER(b.bookDetails.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    Page<Book> findAllByBookDetailsAuthor(Pageable pageable, @Param("author") String author);

    @Query("SELECT b FROM Book b WHERE LOWER(b.bookDetails.title) LIKE LOWER(CONCAT('%', :title, '%'))" +
            " AND LOWER(b.bookDetails.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    Page<Book> findAllByBookDetailsTitleAndBookDetailsAuthor(Pageable pageable,
                                                             @Param("title") String title,
                                                             @Param("author") String author);

}
