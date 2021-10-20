package com.example.user.controller;

import com.example.user.model.User;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(final UserService service) {
        this.service = service;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody final User user) {
        this.service.create(user);
    }

    @GetMapping("/user/{userName}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User get(@PathVariable("userName") final String userName) {
        return this.service.findByUserName(userName);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<User> get() {
        return this.service.findAll();
    }


}
