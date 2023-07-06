package com.example.libraryapp.service;

import com.example.libraryapp.utils.ApiKeyRetriever;
import com.example.libraryapp.exception.BookDetailsNotFoundException;
import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.model.google_books.GoogleBooksDetails;
import com.example.libraryapp.model.google_books.VolumeInfo;
import com.example.libraryapp.repository.BookDetailsRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BookDetailsService {
    private final BookDetailsRepository bookDetailsRepository;
    private final RestTemplate restTemplate;
    private final ApiKeyRetriever retriever;

    public BookDetailsService(BookDetailsRepository bookDetailsRepository, RestTemplate restTemplate, ApiKeyRetriever retriever) {
        this.bookDetailsRepository = bookDetailsRepository;
        this.restTemplate = restTemplate;
        this.retriever = retriever;
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

    public BookDetails findBookDetailsInGoogleBooksApiUsingIsbn(String isbn) {
        ResponseEntity<GoogleBooksDetails> response = restTemplate
                .exchange("https://www.googleapis.com/books/v1/volumes?q=isbn:%s&keyes&key=%s"
                                .formatted(isbn, retriever.getGoogleBooksApiKey()),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<GoogleBooksDetails>() {
                });
        try {
            VolumeInfo volumeInfo = response.getBody().items().get(0).volumeInfo();
            BookDetails bookDetails = new BookDetails();
            bookDetails.setIsbn(isbn);
            bookDetails.setAuthor(volumeInfo.authors().get(0));
            bookDetails.setTitle(volumeInfo.title());
            bookDetails.setPublisher(volumeInfo.publisher());
            bookDetails.setNumberOfPages(volumeInfo.pageCount());
            bookDetails.setLanguage(volumeInfo.language());
            return bookDetails;
        } catch (NullPointerException e) {
            return new BookDetails();
        }
    }
}
