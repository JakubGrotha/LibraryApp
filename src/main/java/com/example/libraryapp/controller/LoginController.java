package com.example.libraryapp.controller;

import com.example.libraryapp.exception.UserRoleNotFoundException;
import com.example.libraryapp.model.LoginRequest;
import com.example.libraryapp.model.User;
import com.example.libraryapp.model.UserRole;
import com.example.libraryapp.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;

import static com.example.libraryapp.model.UserRole.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginRequest loginRequest) {
        String username = loginRequest.username();
        String password = loginRequest.password();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/default";
    }

    @GetMapping("/default")
    public String defaultAfterLogin() {
        UserRole userRole = SecurityUtils.getCurrentUserRole();
        String email = SecurityUtils.getCurrentUserEmail();
        return switch (userRole) {
            case ADMIN -> "redirect:/admin";
            case LIBRARIAN -> "redirect:/librarian";
            case USER -> "redirect:/user";
            case null ->
                    throw new UserRoleNotFoundException(STR."No user role has been found for this email: \{email}");
        };
    }
}
