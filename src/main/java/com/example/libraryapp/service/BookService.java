package com.example.libraryapp.service;

import com.example.libraryapp.exception.BookNotFoundException;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addNewBook(Book book) {
        bookRepository.save(book);
    }

    public void registerReturn(Book book) {
        Book returnedBook = findBookById(book.getId());
        bookRepository.delete(returnedBook);
        bookRepository.save(book);
    }

    public void deleteBookById(long bookId) {
        bookRepository.deleteById(bookId);
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    public Book findBookById(long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("No book found with the following ID: %d".formatted(bookId)));
    }

    public Book findBookByBarcode(Integer barcode) {
        return bookRepository.findBookByBarcode(barcode)
                .orElseThrow(() -> new BookNotFoundException("No book found with the following ISBN: %d".formatted(barcode)));
    }

    public boolean checkIfIsbnHasCorrectFormat(String isbn) {
        return isbn.matches("/d/d/d-/d/d-/d/d-/d/d/d/d/d-/d");
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
