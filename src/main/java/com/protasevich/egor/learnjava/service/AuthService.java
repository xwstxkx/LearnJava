package com.protasevich.egor.learnjava.service;

import com.protasevich.egor.learnjava.exceptions.BadCredentials;
import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.exceptions.PasswordDoNotMatch;
import com.protasevich.egor.learnjava.exceptions.UserIsAlreadyExists;
import com.protasevich.egor.learnjava.entity.UserEntity;
import com.protasevich.egor.learnjava.model.JwtRequest;
import com.protasevich.egor.learnjava.model.JwtResponse;
import com.protasevich.egor.learnjava.model.RegistrationRequest;
import com.protasevich.egor.learnjava.model.UserModel;
import com.protasevich.egor.learnjava.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService service;
    private final JwtTokenUtils utils;
    private final AuthenticationManager authentication;


    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest)
            throws BadCredentials {

        try {
            authentication.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()
            ));
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            throw new BadCredentials();
        }

        UserDetails userDetails = service.loadUserByUsername(authRequest.getUsername());
        String token = utils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));

    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationRequest request)
            throws PasswordDoNotMatch, UserIsAlreadyExists, ObjectNotFound {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordDoNotMatch();
        }

        if (service.findByUsername(request.getUsername()).isPresent()) {
            throw new UserIsAlreadyExists();
        }

        UserEntity user = service.createNewUser(request);
        return ResponseEntity.ok(new UserModel(user.getId(), user.getUsername(), user.getEmail()));
    }
}