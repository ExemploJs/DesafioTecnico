package com.example.account.controller;

import com.example.account.request.AccountCreationRequest;
import com.example.account.response.AccountResponse;
import com.example.account.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(final UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/user/{id}/account")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable("id") final Long id, @RequestBody final AccountCreationRequest request) {
        this.userAccountService.create(id, request);
    }

    @PutMapping("/user/{id}/account/inactivate")
    @ResponseStatus(HttpStatus.OK)
    public void inactivate(@PathVariable("id") final Long id) {
        this.userAccountService.inactivate(id);
    }

    @GetMapping("/user/{id}/account")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountResponse get(@PathVariable("id") final Long id) {
        return this.userAccountService.getAccount(id);
    }
}
