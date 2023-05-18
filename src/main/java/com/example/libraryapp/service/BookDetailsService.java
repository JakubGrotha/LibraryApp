package com.example.libraryapp.service;

import com.example.libraryapp.exception.BookDetailsNotFoundException;
import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.repository.BookDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookDetailsService {
    private final BookDetailsRepository bookDetailsRepository;

    public BookDetailsService(BookDetailsRepository bookDetailsRepository) {
        this.bookDetailsRepository = bookDetailsRepository;
    }

    public List<BookDetails> getAllBookDetails() {
        return bookDetailsRepository.findAll();
    }

    public BookDetails findBookDetailsById(long bookDetailsId) {
        return bookDetailsRepository.findById(bookDetailsId)
                .orElseThrow(() -> new BookDetailsNotFoundException("No book found with the following id: %s"
                        .formatted(bookDetailsId)));
    }

    public BookDetails findBookDetailsByIsbn(String bookIsbn) {
        return bookDetailsRepository.findBookByIsbn(bookIsbn)
                .orElseThrow(() -> new BookDetailsNotFoundException("No book found with the following ISBN: %s"
                        .formatted(bookIsbn)));
    }

    public void addNewBookDetails(BookDetails bookDetailsToAdd) {
        bookDetailsRepository.save(bookDetailsToAdd);
    }

    public void updateBookDetails(BookDetails bookDetails) {
        bookDetailsRepository.save(bookDetails);
    }
}
