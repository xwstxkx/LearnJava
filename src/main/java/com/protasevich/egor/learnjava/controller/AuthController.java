package com.protasevich.egor.learnjava.controller;

import com.protasevich.egor.learnjava.exceptions.BadCredentials;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.PasswordDoNotMatch;
import com.protasevich.egor.learnjava.exceptions.UserIsAlreadyExists;
import com.protasevich.egor.learnjava.dto.JwtRequest;
import com.protasevich.egor.learnjava.dto.RegistrationRequest;
import com.protasevich.egor.learnjava.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "Генерация JWT-токена")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest)
            throws BadCredentials {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.createAuthToken(authRequest));
    }

    @PostMapping("/registration")
    @Operation(summary = "Регистрация пользователя")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationRequest request)
            throws PasswordDoNotMatch, UserIsAlreadyExists, ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createNewUser(request));
    }

}
