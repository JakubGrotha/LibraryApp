package com.example.libraryapp.service;

import com.example.libraryapp.model.User;
import com.example.libraryapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.libraryapp.model.UserRole.*;

@Service
@Transactional
public class LibrarianManagerService {

    private final UserRepository userRepository;

    public LibrarianManagerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllLibrarians() {
        List<User> users = userRepository.findAll()
                .stream()
                .filter(user -> user.getRole().equals(LIBRARIAN))
                .toList();
        System.out.println(users);
        return users;
    }
}
