package com.example.libraryapp.service;

import com.example.libraryapp.model.User;
import com.example.libraryapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.libraryapp.model.UserRole.*;

@Service
@Transactional
@RequiredArgsConstructor
public class LibrarianManagerService {

    private final UserRepository userRepository;

    public List<User> getAllLibrarians() {
        return userRepository.findByUserRole(LIBRARIAN);
    }
}
