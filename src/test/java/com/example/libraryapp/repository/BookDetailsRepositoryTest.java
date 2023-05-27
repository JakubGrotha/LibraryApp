package com.example.libraryapp.repository;

import com.example.libraryapp.model.BookDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.util.Optional;

import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestConstructor(autowireMode = ALL)
class BookDetailsRepositoryTest {
    private static final String ISBN = "978-0-261-10221-7";

    private final BookDetailsRepository bookDetailsRepository;

    BookDetailsRepositoryTest(BookDetailsRepository bookDetailsRepository) {
        this.bookDetailsRepository = bookDetailsRepository;
    }

    @Test
    void findBookDetailsByIsbnReturnsCorrectBookDetailsObject() {
        // given
        String isbn = ISBN;
        // when
        Optional<BookDetails> bookDetails = bookDetailsRepository.findBookByIsbn(isbn);
        // then
        assertTrue(bookDetails.isPresent());
        assertEquals(isbn, bookDetails.get().getIsbn());
        assertEquals("J.R.R. Tolkien", bookDetails.get().getAuthor());
        assertEquals(1, bookDetails.get().getId());
    }

    @Test
    void findBookDetailsByIsbnReturnsEmptyOptionalWhenIsbnIsNotInDatabase() {
        // given
        String wrongIsbn = "4324234";
        // when
        Optional<BookDetails> bookDetails = bookDetailsRepository.findBookByIsbn(wrongIsbn);
        // then
        assertFalse(bookDetails.isPresent());
    }
}
