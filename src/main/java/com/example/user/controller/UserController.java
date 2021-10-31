package com.example.user.controller;

import com.example.user.request.UserRequest;
import com.example.user.request.response.UserResponse;
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
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final UserRequest request) {
        this.service.create(request);
    }

    @GetMapping("/user/{userName}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserResponse get(@PathVariable("userName") final String userName) {
        return this.service.findByUserName(userName);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserResponse> get() {
        return this.service.findAll();
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("userId") final Long userId) {
        this.service.delete(userId);
    }

    @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
        this.service.delete();
    }
}
