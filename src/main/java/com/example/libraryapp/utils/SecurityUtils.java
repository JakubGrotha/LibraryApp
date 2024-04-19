package com.example.libraryapp.utils;

import com.example.libraryapp.model.UserRole;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static com.example.libraryapp.model.UserRole.*;

@UtilityClass
public class SecurityUtils {

    public static String getCurrentUserEmail() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    public static UserRole getCurrentUserRole() {
        String role = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow();
        return mapToUserRole(role);
    }

    private static UserRole mapToUserRole(String role) {
        return switch (role) {
            case "ROLE_ADMIN" -> ADMIN;
            case "ROLE_LIBRARIAN" -> LIBRARIAN;
            case "ROLE_USER" -> USER;
            case null, default -> null;
        };
    }
}
