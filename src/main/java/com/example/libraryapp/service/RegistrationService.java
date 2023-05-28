package com.example.libraryapp.service;

import com.example.libraryapp.model.RegistrationRequest;
import com.example.libraryapp.model.User;
import com.example.libraryapp.model.UserRole;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserService userService;

    public RegistrationService(UserService userService) {
        this.userService = userService;
    }

    public void register(RegistrationRequest registrationRequest) {
        userService.signUpUser(new User(
                registrationRequest.name(),
                registrationRequest.email(),
                registrationRequest.password(),
                UserRole.USER
                ));
    }

    public void register(User user) {
        userService.signUpUser(user);
    }
}
