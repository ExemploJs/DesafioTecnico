package com.example.user.account.controller;

import com.example.exception.AccountException;
import com.example.user.account.model.Account;
import com.example.user.account.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/user/{id}/account")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable("id") final Long id, @RequestBody final Account account) {
        this.userAccountService.create(id, account);
    }

    @PutMapping("/user/{id}/account/inactivate")
    @ResponseStatus(HttpStatus.OK)
    public void inactivate(@PathVariable("id") final Long id) {
        this.userAccountService.inactivate(id);
    }

    @GetMapping("/user/{id}/account")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account get(@PathVariable("id") final Long id) {
        return this.userAccountService.getAccount(id);
    }
}
