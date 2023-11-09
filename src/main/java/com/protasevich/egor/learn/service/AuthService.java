package com.protasevich.egor.learn.service;

import com.protasevich.egor.learn.exceptions.BadCredentials;
import com.protasevich.egor.learn.exceptions.ObjectNotFound;
import com.protasevich.egor.learn.exceptions.PasswordDoNotMatch;
import com.protasevich.egor.learn.exceptions.UserIsAlreadyExists;
import com.protasevich.egor.learn.entity.UserEntity;
import com.protasevich.egor.learn.model.JwtRequest;
import com.protasevich.egor.learn.model.JwtResponse;
import com.protasevich.egor.learn.model.RegistrationRequest;
import com.protasevich.egor.learn.model.UserModel;
import com.protasevich.egor.learn.util.JwtTokenUtils;
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
