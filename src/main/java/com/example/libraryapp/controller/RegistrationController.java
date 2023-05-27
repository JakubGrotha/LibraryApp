package com.example.libraryapp.controller;

import com.example.libraryapp.model.RegistrationRequest;
import com.example.libraryapp.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping()
    public String getRegistrationForm() {
        return "register";
    }

    @PostMapping
    public String register(@RequestParam("name") String name,
                         @RequestParam("email") String email,
                         @RequestParam("password") String password) {
        RegistrationRequest registrationRequest = new RegistrationRequest(name, email, password);
        registrationService.register(registrationRequest);
        return "redirect:/";
    }
}
