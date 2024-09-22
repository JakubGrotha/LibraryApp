package com.example.libraryapp.utils;

import com.example.libraryapp.model.UserRole;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.stream.Stream;

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

        return Stream.of(UserRole.values())
                .filter(userRole -> userRole.securityRole.equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new UserRoleNotFoundException("Wrong user role: %s".formatted(role)));
    }

    private static class UserRoleNotFoundException extends RuntimeException {

        private UserRoleNotFoundException(String message) {
            super(message);
        }
    }
}
