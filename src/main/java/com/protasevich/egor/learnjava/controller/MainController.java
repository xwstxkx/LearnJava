package com.protasevich.egor.learnjava.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/main")
public class MainController {

    @GetMapping("/unsecured")
    @Operation(summary = "Отображение незащищённой информации")
    public String unsecuredData() {
        return "Unsecured data";
    }

    @GetMapping("/secured")
    @Operation(summary = "Отображение защищённой информации")
    public String securedData() {
        return "Secured data";
    }

    @GetMapping("/info")
    @Operation(summary = "Отображение информации, к которой имеет доступ авторизированный пользователь")
    public String userData(Principal principal) {
        //Principal - текущий пользователь
        return principal.getName();
    }
}
