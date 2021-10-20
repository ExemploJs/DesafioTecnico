package com.example.user.account.service;

import com.example.user.account.model.Account;
import com.example.user.account.repository.AccountRepository;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserAccountService(final AccountRepository accountRepository, final UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public void create(final Long userId, final Account account) {
        final User user = Optional.of(this.userRepository.findById(userId))
                .get()
                .orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado!"));

        account.setUser(user);

        this.accountRepository.save(account);
    }

    public void inactivate(final Long userId) {
        final Account account = this.accountRepository.findByUserId(userId);
        account.setActive(false);
    }

    public Account getAccount(final Long id) {
        return this.accountRepository.findByUserId(id);
    }
}
