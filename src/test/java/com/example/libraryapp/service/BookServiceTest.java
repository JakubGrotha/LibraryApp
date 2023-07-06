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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void filteredBooksReturnsAllBooksIfBothCriteriaAreNull() {
        // GIVEN
        List<Book> allBooks = getListOfAllBooks();
        Pageable pageable = Pageable.ofSize(10).withPage(1);
        // WHEN
        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(allBooks));
        Page<Book> result = bookService.filteredBooks(pageable, null, null);
        // THEN
        assertThat(result)
                .containsAll(allBooks)
                .hasSize(6);
    }

    @Test
    void filteredBooksReturnsBooksWithOnlyFilteredTitle() {
        // GIVEN
        String titleToFilter = "Correct";
        List<Book> allBooks = getListOfAllBooks();
        List<Book> booksWithFilteredTitle = allBooks.stream()
                .filter(book -> book.getBookDetails().getTitle().toLowerCase()
                        .contains(titleToFilter.toLowerCase()))
                .toList();
        Pageable pageable = Pageable.ofSize(10).withPage(1);
        // WHEN
        when(bookRepository.findAllByBookDetailsTitle(pageable, titleToFilter))
                .thenReturn(new PageImpl<>(booksWithFilteredTitle));
        Page<Book> result = bookService.filteredBooks(pageable, "Correct", null);
        // THEN
        assertThat(result)
                .containsAll(booksWithFilteredTitle)
                .hasSize(4);
    }

    @Test
    void filteredBooksReturnsBooksWithOnlyFilteredAuthor() {
        // GIVEN
        String authorToFilter = "Correct";
        List<Book> allBooks = getListOfAllBooks();
        List<Book> booksWithFilteredTitle = allBooks.stream()
                .filter(book -> book.getBookDetails().getTitle().toLowerCase()
                        .contains(authorToFilter.toLowerCase()))
                .toList();
        Pageable pageable = Pageable.ofSize(10).withPage(1);
        // WHEN
        when(bookRepository.findAllByBookDetailsAuthor(pageable, authorToFilter))
                .thenReturn(new PageImpl<>(booksWithFilteredTitle));
        Page<Book> result = bookService.filteredBooks(pageable, null, "Correct");
        // THEN
        assertThat(result)
                .containsAll(booksWithFilteredTitle)
                .hasSize(4);
    }

    @Test
    void filteredBooksReturnsBooksWithBothFilteredAuthorAndFilteredTitle() {
        // GIVEN
        String authorToFilter = "Correct";
        String titleToFilter = "Correct";
        List<Book> allBooks = getListOfAllBooks();
        List<Book> booksWithFilteredTitle = allBooks.stream()
                .filter(book -> book.getBookDetails().getTitle().toLowerCase()
                        .contains(titleToFilter.toLowerCase()))
                .filter(book -> book.getBookDetails().getAuthor().toLowerCase()
                        .contains(authorToFilter.toLowerCase()))
                .toList();
        Pageable pageable = Pageable.ofSize(10).withPage(1);
        // WHEN
        when(bookRepository.findAllByBookDetailsTitleAndBookDetailsAuthor(pageable, authorToFilter, titleToFilter))
                .thenReturn(new PageImpl<>(booksWithFilteredTitle));
        Page<Book> result = bookService.filteredBooks(pageable, "Correct", "Correct");
        // THEN
        assertThat(result)
                .containsAll(booksWithFilteredTitle)
                .hasSize(3);
    }

    private List<Book> getListOfAllBooks() {
        return List.of(
                new Book(1, "123123", new BookDetails(
                        1, "123-123", "Correct Author", "correct Title",
                        null, null, null, null, null
                ), null, true),
                new Book(2, "222222", new BookDetails(
                        2, "222-123", "Correct Aut", "correct Ti",
                        null, null, null, null, null
                ), null, false),
                new Book(3, "123123454", new BookDetails(
                        2, "221-123", "New Correct Aut", "New correct Ti",
                        null, null, null, null, null
                ), null, false),
                new Book(3, "123123454", new BookDetails(
                        2, "221-123", "New Correct Aut", "New Ti",
                        null, null, null, null, null
                ), null, false),
                new Book(3, "123123454", new BookDetails(
                        2, "221-123", "New Aut", "New Correct Ti",
                        null, null, null, null, null
                ), null, false),
                new Book(4, "222111", new BookDetails(
                        2, "212-123", "Random author", "Random Title",
                        null, null, null, null, null
                ), null, false)
        );
    }

}
