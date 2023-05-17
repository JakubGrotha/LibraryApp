package com.example.libraryapp.controller;

import com.example.libraryapp.model.Book;
import com.example.libraryapp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("librarian")
public class LibrarianBookController {

    private final BookService bookService;

    public LibrarianBookController(BookService bookService) {
        this.bookService = bookService;
    }

    private static final String REDIRECT_TO_ALL_BOOKS = "redirect:/librarian/books";

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "librarian/books";
    }

    @GetMapping("new")
    public String showNewBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "librarian/new-book";
    }

    @PostMapping("books")
    public String addNewBook(Book book) {
        book.setAvailable(true);
        bookService.addNewBook(book);
        return REDIRECT_TO_ALL_BOOKS;
    }

    @GetMapping("/books/{id}")
    public String viewOneBook(@PathVariable long id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return "librarian/book-details";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        bookService.deleteBookById(id);
        return REDIRECT_TO_ALL_BOOKS;
    }

    @GetMapping("/books/edition-form/{id}")
    public String getBookEditionForm(@PathVariable long id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return "librarian/book-edition";
    }

    @PostMapping("/books/edition/{id}")
    public String bookPutMethod(@PathVariable long id, @ModelAttribute Book book) {
        bookService.updateBook(book);
        return REDIRECT_TO_ALL_BOOKS;
    }
}
