package com.example.libraryapp.service;

import com.example.libraryapp.configuration.GoogleBooksApiConfiguration;
import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.model.google_books.GoogleBooksDetails;
import com.example.libraryapp.model.google_books.Volume;
import com.example.libraryapp.model.google_books.VolumeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class GoogleBooksApiService {

    private final GoogleBooksApiConfiguration configuration;
    private final RestClient restClient;

    public BookDetails findBookDetailsInGoogleBooksApiUsingIsbn(String isbn) {
        if (!configuration.isEnabled()) {
            return new BookDetails();
        }
        String apiKey = configuration.getKey();
        ResponseEntity<GoogleBooksDetails> response = restClient.get()
                .uri(STR."https://www.googleapis.com/books/v1/volumes?q=isbn:\{isbn}&keyes&key=\{apiKey}")
                .retrieve()
                .toEntity(GoogleBooksDetails.class);

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
