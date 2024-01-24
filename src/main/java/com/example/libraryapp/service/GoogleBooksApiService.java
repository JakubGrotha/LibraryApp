package com.example.libraryapp.service;

import com.example.libraryapp.configuration.GoogleBooksApiConfiguration;
import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.model.google_books.GoogleBooksDetails;
import com.example.libraryapp.model.google_books.Volume;
import com.example.libraryapp.model.google_books.VolumeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class GoogleBooksApiService {

    private final GoogleBooksApiConfiguration configuration;
    private final RestTemplate restTemplate;

    public BookDetails findBookDetailsInGoogleBooksApiUsingIsbn(String isbn) {
        if (!configuration.isEnabled()) {
            return new BookDetails();
        }
        ResponseEntity<GoogleBooksDetails> response = restTemplate
                .exchange("https://www.googleapis.com/books/v1/volumes?q=isbn:%s&keyes&key=%s"
                                .formatted(isbn, configuration.getKey()),
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<GoogleBooksDetails>() {
                        });

        GoogleBooksDetails googleBooksDetails = response.getBody();
        if (googleBooksDetails == null) {
            return new BookDetails();
        }
        VolumeInfo volumeInfo = googleBooksDetails.items()
                .stream()
                .findFirst()
                .map(Volume::volumeInfo)
                .orElse(null);
        if (volumeInfo == null) {
            return new BookDetails();
        }

        BookDetails bookDetails = new BookDetails();
        bookDetails.setIsbn(isbn);
        bookDetails.setAuthor(volumeInfo.authors().get(0));
        bookDetails.setTitle(volumeInfo.title());
        bookDetails.setPublisher(volumeInfo.publisher());
        bookDetails.setNumberOfPages(volumeInfo.pageCount());
        bookDetails.setLanguage(volumeInfo.language());
        return bookDetails;
    }
}
