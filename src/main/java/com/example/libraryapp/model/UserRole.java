package com.example.libraryapp.model;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    LIBRARIAN("ROLE_LIBRARIAN"),
    USER("ROLE_USER");

    public final String securityRole;

    UserRole(String securityRole) {
        this.securityRole = securityRole;
    }
}
