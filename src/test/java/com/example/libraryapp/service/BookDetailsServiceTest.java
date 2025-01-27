package com.example.libraryapp.service;

import com.example.libraryapp.exception.BookDetailsNotFoundException;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.repository.BookDetailsRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.libraryapp.service.BookDetailsService.LookupResult;
import static com.example.libraryapp.service.BookDetailsService.LookupResult.FoundInDatabase;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookDetailsServiceTest {

    private final BookDetailsRepository bookDetailsRepository = mock(BookDetailsRepository.class);
    private final GoogleBooksApiService googleBooksApiService = mock(GoogleBooksApiService.class);
    private final BookDetailsService bookDetailsService = new BookDetailsService(
            bookDetailsRepository, googleBooksApiService
    );

    @Test
    void getAllBookDetailsReturnsAllBookDetails() {
        // given
        List<BookDetails> bookDetails = Arrays.asList(
                new BookDetails(1, "978-0-261-10221-7", "J.R.R. Tolkien", "The Hobbit",
                        "HarperCollinsPublisher", 389,
                        "fantasy", "English", new ArrayList<Book>()),
                new BookDetails(2, "978-82-84190-22-8", "Per Erik Stenstrøm", "Irriterende kolleger",
                        "Strawberry Publishing AS", 239,
                        "guide book", "Norwegian", new ArrayList<Book>()),
                new BookDetails(3, "978-1-78470-835-1", "Michael Ondaatje", "Warlight",
                        "Vintage", 290,
                        "novel", "English", new ArrayList<Book>())
        );
        // when
        when(bookDetailsRepository.findAll()).thenReturn(bookDetails);
        List<BookDetails> bookDetailsList = bookDetailsService.getAllBookDetails();
        // then
        assertThat(bookDetailsList)
                .hasSize(3)
                .doesNotContainNull()
                .contains(bookDetails.get(0))
                .contains(bookDetails.get(1))
                .contains(bookDetails.get(2));

    }

    @Test
    void findBookDetailsByIdReturnsCorrectBookDetailsWhenGivenCorrectId() {
        // given
        long bookId = 1;
        BookDetails bookDetailsFromDatabase = new BookDetails(bookId, "978-0-261-10221-7",
                "J.R.R. Tolkien", "The Hobbit",
                "HarperCollinsPublisher", 389,
                "fantasy", "English", new ArrayList<Book>());
        // when
        when(bookDetailsRepository.findById(bookId)).thenReturn(Optional.of(bookDetailsFromDatabase));
        BookDetails bookDetails = bookDetailsService.findBookDetailsById(bookId);
        // then
        assertAll("book details",
                () -> assertEquals(bookDetailsFromDatabase.getId(), bookDetails.getId()),
                () -> assertEquals(bookDetailsFromDatabase.getIsbn(), bookDetails.getIsbn()),
                () -> assertEquals(bookDetailsFromDatabase.getAuthor(), bookDetails.getAuthor()),
                () -> assertEquals(bookDetailsFromDatabase.getLanguage(), bookDetails.getLanguage()),
                () -> assertEquals(bookDetailsFromDatabase.getTitle(), bookDetails.getTitle())
        );
    }

    @Test
    void findBookDetailsByIdThrowsBookDetailsNotFoundExceptionWhenGivenAnEmptyOptional() {
        // GIVEN
        long bookId = 0;
        // WHEN
        when(bookDetailsRepository.findById(bookId)).thenReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> bookDetailsService.findBookDetailsById(bookId))
                .isInstanceOf(BookDetailsNotFoundException.class)
                .hasMessage("No book found with the following id: %s"
                        .formatted(bookId));
    }

    @Test
    void findBookDetailsByIsbnReturnsCorrectBookDetailsWhenGivenCorrectIsbn() {
        // GIVEN
        String bookIsbn = "978-0-261-10221-7";
        BookDetails bookDetailsFromDatabase = new BookDetails(1, bookIsbn,
                "J.R.R. Tolkien", "The Hobbit",
                "HarperCollinsPublisher", 389,
                "fantasy", "English", new ArrayList<Book>());
        // WHEN
        when(bookDetailsRepository.findBookByIsbn(bookIsbn)).thenReturn(Optional.of(bookDetailsFromDatabase));
        LookupResult lookupResult = bookDetailsService.findBookDetailsByIsbn(bookIsbn);
        // THEN
        assertThat(lookupResult).isInstanceOf(FoundInDatabase.class);
        BookDetails bookDetails = ((FoundInDatabase) lookupResult).bookDetails();
        assertAll("book details",
                () -> assertEquals(bookDetailsFromDatabase.getId(), bookDetails.getId()),
                () -> assertEquals(bookDetailsFromDatabase.getIsbn(), bookDetails.getIsbn()),
                () -> assertEquals(bookDetailsFromDatabase.getAuthor(), bookDetails.getAuthor()),
                () -> assertEquals(bookDetailsFromDatabase.getLanguage(), bookDetails.getLanguage()),
                () -> assertEquals(bookDetailsFromDatabase.getTitle(), bookDetails.getTitle())
        );
    }
}
