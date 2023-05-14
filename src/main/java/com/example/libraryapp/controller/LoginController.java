package com.example.libraryapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping("login")
    public String getLoginForm() {
        return "login";
    }

    @GetMapping("default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            System.out.println("admin");
            return "redirect:/admin/";
        }
        if (request.isUserInRole("ROLE_LIBRARIAN")) {
            System.out.println("librarian");
            return "redirect:/librarian/";
        }
        return "redirect:/user/";
    }
}
