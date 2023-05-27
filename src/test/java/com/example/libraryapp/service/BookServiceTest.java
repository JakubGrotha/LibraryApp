package com.example.libraryapp.service;

import com.example.libraryapp.exception.BookNotFoundException;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.model.Loan;
import com.example.libraryapp.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @Test
    void findBookByIdReturnsCorrectBook() {
        // given
        long bookId = 1;
        Book bookFromDatabase = new Book(bookId, "9780261102217", new BookDetails(), new Loan(), true);
        // when
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookFromDatabase));
        Book book = bookService.findBookById(bookId);
        // then
        assertAll("book",
                () -> assertEquals(bookFromDatabase.getId(), book.getId()),
                () -> assertEquals(bookFromDatabase.getBarcode(), book.getBarcode())
        );
    }

    @Test
    void findBookByIdThrowsProperExceptionWhenIdIsIncorrect() {
        // given
        long bookId = 0;
        // when
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> bookService.findBookById(bookId))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("No book found with the following ID: %d".formatted(bookId));
    }

    @Test
    void findBookByBarcodeReturnsCorrectBook() {
        // given
        String barcode = "9780261102217";
        Book bookFromDatabase = new Book(1, barcode, new BookDetails(), new Loan(), true);
        // when
        when(bookRepository.findBookByBarcode(barcode)).thenReturn(Optional.of(bookFromDatabase));
        Book book = bookService.findBookByBarcode(barcode);
        // then
        assertAll("book",
                () -> assertEquals(bookFromDatabase.getId(), book.getId()),
                () -> assertEquals(bookFromDatabase.getBarcode(), book.getBarcode())
        );
    }

    @Test
    void findBookByBarcodeThrowsProperExceptionWhenBarcodeIsIncorrect() {
        // given
        String barcode = "9780261102217";
        // when
        when(bookRepository.findBookByBarcode(barcode)).thenReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> bookService.findBookByBarcode(barcode))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("No book found with the following barcode: %s".formatted(barcode));
    }
}
