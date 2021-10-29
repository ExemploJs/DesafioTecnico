package com.example.user.account.service;

import com.example.exception.AccountException;
import com.example.user.account.model.Account;
import com.example.user.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService(final AccountRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void save(final Account account) {
        this.repository.save(account);
    }

    public Account findByUserId(final Long userId) {
        return Optional
                .of(this.repository.findByUserId(userId))
                .orElseThrow(AccountException::new);
    }
}
