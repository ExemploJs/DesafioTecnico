package com.example.user.account.controller;

import com.example.user.account.request.AccountCreationRequest;
import com.example.user.account.response.AccountResponse;
import com.example.user.account.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<AccountResponse> get(@PathVariable("id") final Long id) {
        return this.userAccountService.getAccounts(id);
    }

    @DeleteMapping("/user/{id}/account")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByUserId(@PathVariable("id") final Long id) {
        this.userAccountService.deleteByUserId(id);
    }
}
