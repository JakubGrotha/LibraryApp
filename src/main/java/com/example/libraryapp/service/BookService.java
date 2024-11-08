package com.example.libraryapp.service;

import com.example.libraryapp.exception.BookNotFoundException;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.repository.BookRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException("No book found with the following ID: %s".formatted(bookId))
        );
    }

    public Book findBookByBarcode(String barcode) {
        return bookRepository.findBookByBarcode(barcode).orElseThrow(
                () -> new BookNotFoundException("No book found with the following barcode: %s".formatted(barcode))
        );
    }

    public Page<Book> getFilteredBooks(Pageable pageable, String title, String author) {
        boolean isTitlePresent = StringUtils.isNotBlank(title);
        boolean isAuthorPresent = StringUtils.isNotBlank(author);
        if (isTitlePresent && isAuthorPresent) {
            return bookRepository.findAllByBookDetailsTitleAndBookDetailsAuthor(pageable, title, author);
        } else if (isTitlePresent) {
            return bookRepository.findAllByBookDetailsTitle(pageable, title);
        } else if (isAuthorPresent) {
            return bookRepository.findAllByBookDetailsAuthor(pageable, author);
        } else
            return bookRepository.findAll(pageable);
    }
}
