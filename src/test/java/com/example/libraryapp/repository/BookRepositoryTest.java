package com.example.libraryapp.repository;

import com.example.libraryapp.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.TestConstructor.AutowireMode.*;

@SpringBootTest
@TestConstructor(autowireMode = ALL)
class BookRepositoryTest {

    private static final String BOOK_BARCODE = "9780261102217";
    private final BookRepository bookRepository;

    BookRepositoryTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    void findBookByBarcodeReturnsCorrectBookObject() {
        // given
        String barcode = BOOK_BARCODE;
        // when
        Optional<Book> book = bookRepository.findBookByBarcode(barcode);
        // then
        assertTrue(book.isPresent());
        assertEquals(1, book.get().getId());
        assertTrue(book.get().isAvailable());
    }

    @Test
    void findBookByBarcodeReturnsEmptyOptionalWhenNoBookIsFound() {
        // given
        String wrongBarcode = "000000000";
        // when
        Optional<Book> book = bookRepository.findBookByBarcode(wrongBarcode);
        // then
        assertFalse(book.isPresent());
    }
}
