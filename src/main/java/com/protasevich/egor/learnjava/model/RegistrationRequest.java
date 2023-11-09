package com.protasevich.egor.learnjava.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegistrationRequest {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;

}
