package com.protasevich.egor.learnjava.controller;

import com.protasevich.egor.learnjava.exceptions.BadCredentials;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.PasswordDoNotMatch;
import com.protasevich.egor.learnjava.exceptions.UserIsAlreadyExists;
import com.protasevich.egor.learnjava.service.AuthService;
import com.protasevich.egor.learnjava.model.JwtRequest;
import com.protasevich.egor.learnjava.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthController {

    private final AuthService service;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) throws BadCredentials {
        return service.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationRequest request)
            throws PasswordDoNotMatch, UserIsAlreadyExists, ObjectNotFound {
        return service.createNewUser(request);
    }

}
