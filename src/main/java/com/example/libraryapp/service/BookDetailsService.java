package com.example.libraryapp.service;

import com.example.libraryapp.exception.BookDetailsNotFoundException;
import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.repository.BookDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.libraryapp.service.BookDetailsService.LookupResult.FoundInDatabase;
import static com.example.libraryapp.service.BookDetailsService.LookupResult.FoundInGoogleBooks;
import static com.example.libraryapp.service.BookDetailsService.LookupResult.NotFound;

@Service
@Transactional
@RequiredArgsConstructor
public class BookDetailsService {

    private final BookDetailsRepository bookDetailsRepository;
    private final GoogleBooksApiService googleBooksApiService;

    public List<BookDetails> getAllBookDetails() {
        return bookDetailsRepository.findAll();
    }

    public BookDetails findBookDetailsById(long bookDetailsId) {
        return bookDetailsRepository.findById(bookDetailsId)
                .orElseThrow(() -> new BookDetailsNotFoundException("No book found with the following id: %s"
                        .formatted(bookDetailsId)));
    }

    public LookupResult findBookDetailsByIsbn(String bookIsbn) {
        Optional<BookDetails> bookDetailsFromRepository = bookDetailsRepository.findBookByIsbn(bookIsbn);
        if (bookDetailsFromRepository.isPresent()) {
            return new FoundInDatabase(bookDetailsFromRepository.get());
        }
        Optional<BookDetails> bookDetailsFromGoogleBooksApi =
                googleBooksApiService.findBookDetailsInGoogleBooksApiUsingIsbn(bookIsbn);
        if (bookDetailsFromGoogleBooksApi.isPresent()) {
            return new FoundInGoogleBooks(bookDetailsFromGoogleBooksApi.get());
        }
        return new NotFound();

    }

    public void addNewBookDetails(BookDetails bookDetailsToAdd) {
        bookDetailsRepository.save(bookDetailsToAdd);
    }

    public void updateBookDetails(BookDetails bookDetails) {
        bookDetailsRepository.save(bookDetails);
    }

    public sealed interface LookupResult {
        record FoundInDatabase(BookDetails bookDetails) implements LookupResult {

        }

        record FoundInGoogleBooks(BookDetails bookDetails) implements LookupResult {

        }

        record NotFound() implements LookupResult {

        }
    }
}
