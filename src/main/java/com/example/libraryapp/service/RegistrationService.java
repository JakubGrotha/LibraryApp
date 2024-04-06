package com.example.libraryapp.service;

import com.example.libraryapp.model.RegistrationRequest;
import com.example.libraryapp.model.User;
import com.example.libraryapp.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;

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
