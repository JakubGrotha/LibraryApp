package com.example.libraryapp.controller;

import com.example.libraryapp.model.Book;
import com.example.libraryapp.service.BookDetailsService;
import com.example.libraryapp.service.BookService;
import com.example.libraryapp.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/librarian")
@RequiredArgsConstructor
public class LibrarianBookController {

    private final BookService bookService;
    private final LoanService loanService;
    private final BookDetailsService bookDetailsService;
    private static final String REDIRECT_TO_ALL_BOOKS = "redirect:/librarian/books";

    @GetMapping("/books")
    public String getAllBooks(
            Model model,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @SortDefault("bookDetails.title") Pageable pageable
    ) {
        Page<Book> books = bookService.filteredBooks(pageable, title, author);
        model.addAttribute("books", books);
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        return "librarian/books";
    }

    @GetMapping("/new")
    public String showNewBookForm(Model model, @RequestParam("id") long bookDetailsId) {
        Book newBook = new Book();
        newBook.setBookDetails(bookDetailsService.findBookDetailsById(bookDetailsId));
        model.addAttribute("book", newBook);
        return "librarian/new-book";
    }

    @PostMapping("/books")
    public String addNewBook(@ModelAttribute Book book) {
        book.setAvailable(true);
        bookDetailsService.updateBookDetails(book.getBookDetails());
        bookService.addNewBook(book);
        return REDIRECT_TO_ALL_BOOKS;
    }

    @GetMapping("/books/{id}")
    public String viewOneBook(@PathVariable long id, Model model) {
        Book book = bookService.findBookById(id);
        model.addAttribute("book", book);
        if (!book.isAvailable()) {
            model.addAttribute("loan", loanService.findLoanById(book.getLoan().getId()));
        }
        return "librarian/single-book";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        bookService.deleteBookById(id);
        return REDIRECT_TO_ALL_BOOKS;
    }

    @GetMapping("/books/edition-form/{id}")
    public String getBookEditionForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return "librarian/book-edition";
    }

    @PostMapping("/books/edition")
    public String bookPutMethod(@ModelAttribute Book book) {
        bookService.updateBook(book);
        return REDIRECT_TO_ALL_BOOKS;
    }
}
