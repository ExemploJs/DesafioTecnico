package com.example.user.account.service;

import com.example.exception.UserNotFoundException;
import com.example.user.account.model.Account;
import com.example.user.account.repository.AccountRepository;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountService {

    private final AccountService accountService;
    private final UserRepository userRepository;

    @Autowired
    public UserAccountService(final AccountService accountService, final UserRepository userRepository) {
        this.accountService = accountService;
        this.userRepository = userRepository;
    }

    public void create(final Long userId, final Account account) {
        final User user = Optional.of(this.userRepository.findById(userId))
                .get()
                .orElseThrow(UserNotFoundException::new);

        account.setUser(user);

        this.accountService.save(account);
    }

    public void inactivate(final Long userId) {
        final Account account = this.accountService.findByUserId(userId);
        account.setActive(false);
    }

    public Account getAccount(final Long id) {
        return this.accountService.findByUserId(id);
    }
}
