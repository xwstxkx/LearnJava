package com.protasevich.egor.learnjava.model;

import lombok.Data;

@Data
public class JwtRequest {

    private String username;
    private String password;

}
