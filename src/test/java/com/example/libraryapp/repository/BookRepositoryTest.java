package com.example.libraryapp.repository;

import com.example.libraryapp.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findBookByBarcodeReturnsCorrectBookObject() {
        // given
        String barcode = "9780261102217";
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
