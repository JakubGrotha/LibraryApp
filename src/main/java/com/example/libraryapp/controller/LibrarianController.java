package com.example.libraryapp.controller;

import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.User;
import com.example.libraryapp.service.BookService;
import com.example.libraryapp.service.LibraryCardService;
import com.example.libraryapp.service.LoanService;
import com.example.libraryapp.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Period;

import static com.example.libraryapp.model.UserRole.USER;

@Controller
@RequestMapping("/librarian")
@RequiredArgsConstructor
public class LibrarianController {

    private static final String REDIRECT_TO_MAIN_VIEW = "redirect:/librarian";

    private final BookService bookService;
    private final LibraryCardService libraryCardService;
    private final LoanService loanService;
    private final RegistrationService registrationService;

    @GetMapping()
    public String getLibrarianMainMenu() {
        return "librarian/main-view";
    }

    @GetMapping("/user/new")
    public String getNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "librarian/new-user";
    }

    @PostMapping("/user")
    public String addNewUser(@ModelAttribute User user) {
        user.setUserRole(USER);
        user.setPassword("123"); // TODO: Send a link to user to make them change the password
        registrationService.register(user);
        return REDIRECT_TO_MAIN_VIEW;
    }

    @GetMapping("/loan")
    public String getLoanForm() {
        return "librarian/book-loan";
    }

    @PostMapping("/loan")
    public String registerBookLoan(
            @RequestParam("book.barcode") String barcode,
            @RequestParam("libraryCard.number") int libraryCardNumber,
            @RequestParam("duration") int durationInMonths
    ) {
        loanService.loanBook(barcode, libraryCardNumber, Period.ofMonths(durationInMonths));
        return REDIRECT_TO_MAIN_VIEW;
    }

    @GetMapping("/return")
    public String getReturnForm(Model model) {
        model.addAttribute("book", new Book());
        return "librarian/book-return";
    }

    @GetMapping("/return/register")
    public String registerBookReturn(@RequestParam("barcode") String barcode) {
        loanService.registerReturn(barcode);
        return REDIRECT_TO_MAIN_VIEW;
    }
}
