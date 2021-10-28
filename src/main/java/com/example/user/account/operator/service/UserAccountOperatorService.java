package com.example.user.account.operator.service;

import com.example.user.account.model.Account;
import com.example.user.account.operator.request.BillRequest;
import com.example.user.account.operator.request.TransferRequest;
import com.example.user.account.repository.AccountRepository;
import com.example.user.history.model.History;
import com.example.user.history.request.HistoryRequest;
import com.example.user.producer.HistoryProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserAccountOperatorService {

    private final AccountRepository accountRepository;
    private final HistoryProducer historyProducer;

    @Autowired
    public UserAccountOperatorService(final AccountRepository accountRepository,
                                      final HistoryProducer historyProducer) {
        this.accountRepository = accountRepository;
        this.historyProducer = historyProducer;
    }

    public void withdraw(final Long userId, final BigDecimal value) {
        final Account account = getAccount(userId);
        account.withdraw(value);
        this.accountRepository.save(account);
        this.historyProducer.send(getHistoryRequest(History.Operation.WITHDRAW, account,
                String.format("Saque de %s realizado por %s", value.toString(), account.getUser().getUserName())));
    }

    private HistoryRequest getHistoryRequest(final History.Operation operation, final Account account, final String message) {
        final HistoryRequest history = new HistoryRequest();
        history.setOperation(operation.toString());
        history.setUserId(account.getUser().getId());
        history.setMessage(message);
        return history;
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
