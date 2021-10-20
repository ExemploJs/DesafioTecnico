package com.example.user.account.operator.service;

import com.example.user.account.model.Account;
import com.example.user.account.operator.request.BillRequest;
import com.example.user.account.operator.request.TransferRequest;
import com.example.user.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserAccountOperatorService {

    private final AccountRepository accountRepository;

    @Autowired
    public UserAccountOperatorService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void withdraw(final Long userId, final BigDecimal value) {
        final Account account = getAccount(userId);
        account.withdraw(value);
        this.accountRepository.save(account);
    }

    public void deposit(final Long userId, final BigDecimal value) {
        final Account account = getAccount(userId);
        account.deposit(value);
        this.accountRepository.save(account);
    }

    public void transfer(final Long fromUserId,
                         final Long toUserId,
                         final TransferRequest transferRequest) {
        final Account fromAccount = getAccount(fromUserId);
        fromAccount.withdraw(transferRequest.getTransferedValue());
        this.accountRepository.save(fromAccount);

        final Account toAccount = getAccount(toUserId);
        toAccount.deposit(transferRequest.getTransferedValue());
        this.accountRepository.save(toAccount);
    }

    public void payBill(final Long userId, final BillRequest billRequest) {
        final Account account = this.accountRepository.findByUserId(userId);
        account.withdraw(billRequest.getValue());

        this.accountRepository.save(account);
    }

    private Account getAccount(final Long userId) {
        return Optional
                .of(this.accountRepository.findByUserId(userId))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

}
