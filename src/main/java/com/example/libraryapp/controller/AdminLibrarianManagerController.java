package com.example.libraryapp.controller;

import com.example.libraryapp.model.User;
import com.example.libraryapp.service.LibrarianManagerService;
import com.example.libraryapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.libraryapp.model.UserRole.*;

@Controller
@RequestMapping("/admin/librarian-manager")
@RequiredArgsConstructor
public class AdminLibrarianManagerController {

    private final LibrarianManagerService librarianManagerService;
    private final UserService userService;

    @GetMapping()
    public String getLibrarianManagerView() {
        return "admin/librarian-manager";
    }

    @GetMapping("/librarians")
    public String getAllLibrarians(Model model) {
        model.addAttribute("librarians", librarianManagerService.getAllLibrarians());
        return "admin/all-librarians";
    }

    @GetMapping("/new-librarian")
    public String getLibrarianCreationForm(Model model) {
        model.addAttribute("librarian", new User());
        return "admin/new-librarian";
    }

    @PostMapping()
    public String addNewLibrarian(@ModelAttribute User newLibrarian) {
        newLibrarian.setUserRole(LIBRARIAN);
        userService.signUpUser(newLibrarian);
        return "redirect:/admin/librarian-manager";
    }
}
