package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class UserController {

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public void get() {
        System.out.println("GET RECEIVED!");
    }
}
