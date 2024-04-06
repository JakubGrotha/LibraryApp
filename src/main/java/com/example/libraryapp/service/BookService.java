package com.example.libraryapp.service;

import com.example.libraryapp.exception.BookNotFoundException;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.StringTemplate.STR;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

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
                .orElseThrow(() -> new BookNotFoundException(STR."No book found with the following ID: \{bookId}"));
    }

    public Book findBookByBarcode(String barcode) {
        return bookRepository.findBookByBarcode(barcode)
                .orElseThrow(() -> new BookNotFoundException("No book found with the following barcode: %s".formatted(barcode)));
    }

    public Page<Book> filteredBooks(Pageable pageable, String title, String author) {
        boolean titleCheck = title != null && !title.isBlank();
        boolean authorCheck = author != null && !author.isBlank();

        if (titleCheck) {
            if (authorCheck) {
                return bookRepository.findAllByBookDetailsTitleAndBookDetailsAuthor(pageable, title, author);
            } else {
                return bookRepository.findAllByBookDetailsTitle(pageable, title);
            }
        }

        if (authorCheck) {
            return bookRepository.findAllByBookDetailsAuthor(pageable, author);
        }

        return bookRepository.findAll(pageable);
    }
}
