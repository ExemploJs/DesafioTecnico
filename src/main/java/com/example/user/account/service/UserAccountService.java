package com.example.user.account.service;

import com.example.exception.APIException;
import com.example.exception.UserNotFoundException;
import com.example.user.account.entity.Account;
import com.example.user.account.request.AccountCreationRequest;
import com.example.user.account.response.AccountResponse;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void create(final Long userId, final AccountCreationRequest request) {
        final User user = Optional.of(this.userRepository.findById(userId))
                .get()
                .orElseThrow(UserNotFoundException::new);

        try {
            final Account account = this.getAccountEntity(request);
            account.setUser(user);
            account.setActive(true);

            this.accountService.save(account);
        } catch (final Exception e) {
            throw new APIException();
        }
    }

    @Transactional
    public void inactivate(final Long userId) {
        try {
            final Account account = this.accountService.findByUserId(userId);
            account.setActive(false);
        } catch(final Exception e) {
            throw new APIException();
        }
    }

    public AccountResponse getAccount(final Long id) {
        final Account account = this.accountService.findByUserId(id);
        return this.getAccountResponse(account);
    }

    private Account getAccountEntity(final AccountCreationRequest request) {
        final Account account = new Account();
        account.setAgency(request.getAgency());
        account.setAccountNumber(request.getAccountNumber());
        account.setAccountDigit(request.getAccountDigit());
        account.setAgencyDigit(request.getAgencyDigit());
        account.setBalance(request.getBalance());
        return account;
    }

    private AccountResponse getAccountResponse(final Account account) {
        final AccountResponse response = new AccountResponse();
        response.setAgency(account.getAgency());
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountDigit(account.getAccountDigit());
        response.setAgencyDigit(account.getAgencyDigit());
        response.setBalance(account.getBalance());
        response.setActive(account.isActive());
        return response;
    }
}
