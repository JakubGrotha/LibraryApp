package com.example.libraryapp.service;

import com.example.libraryapp.exception.BookDetailsNotFoundException;
import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.repository.BookDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookDetailsService {

    private final BookDetailsRepository bookDetailsRepository;

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
