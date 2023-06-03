package com.example.libraryapp.service;

import com.example.libraryapp.exception.BookNotFoundException;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addNewBook(Book book) {
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

    public Book findBookByBarcode(String barcode) {
        return bookRepository.findBookByBarcode(barcode)
                .orElseThrow(() -> new BookNotFoundException("No book found with the following barcode: %s".formatted(barcode)));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> filteredBooks(String title, String author) {
        List<Book> allBooks = getAllBooks();

        if (title != null && !title.isEmpty()) {
            allBooks = allBooks.stream()
                    .filter(book -> book.getBookDetails().getTitle().toLowerCase()
                            .contains(title.toLowerCase()))
                    .toList();
        }

        if (author != null && !author.isEmpty()) {
            allBooks = allBooks.stream()
                    .filter(book -> book.getBookDetails().getAuthor().toLowerCase()
                            .contains(author.toLowerCase()))
                    .toList();
        }

        return allBooks;
    }
}
