package com.example.user.account.operator.service;

import com.example.user.account.entity.Account;
import com.example.user.account.operator.request.BillRequest;
import com.example.user.account.operator.request.TransferRequest;
import com.example.user.account.service.AccountService;
import com.example.user.history.model.History;
import com.example.user.history.request.HistoryRequest;
import com.example.user.producer.HistoryProducer;
import com.example.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserAccountOperatorService {

    private final AccountService accountService;
    private final HistoryProducer historyProducer;

    @Autowired
    public UserAccountOperatorService(final AccountService accountService,
                                      final HistoryProducer historyProducer) {
        this.accountService = accountService;
        this.historyProducer = historyProducer;
    }

    public void withdraw(final Long userId, final BigDecimal value) {
        final Account account = this.accountService.findActiveByUserId(userId);
        account.withdraw(value);
        this.accountService.save(account);
        this.historyProducer.send(getHistoryRequest(History.Operation.WITHDRAW, account,
                String.format("Saque de %s realizado por %s", value.toString(), account.getUser().getUserName())));
    }

    public void deposit(final Long userId, final BigDecimal value) {
        final Account account = this.accountService.findActiveByUserId(userId);
        account.deposit(value);
        this.accountService.save(account);
        this.historyProducer.send(getHistoryRequest(History.Operation.DEPOSIT, account,
                String.format("Depósito de %s realizado por %s", value.toString(), account.getUser().getUserName())));
    }

    public void transfer(final Long fromUserId,
                         final Long toUserId,
                         final TransferRequest transferRequest) {
        final Account fromAccount = this.accountService.findActiveByUserId(fromUserId);
        fromAccount.withdraw(transferRequest.getTransferedValue());
        this.accountService.save(fromAccount);

        final Account toAccount = this.accountService.findActiveByUserId(toUserId);
        toAccount.deposit(transferRequest.getTransferedValue());
        this.accountService.save(toAccount);

        this.historyProducer.send(getHistoryRequest(History.Operation.TRANSFERENCE, fromAccount,
                String.format("Enviado transferência de %s realizado por %s para %s",
                        transferRequest.getTransferedValue().toString(),
                        fromAccount.getUser().getUserName(), toAccount.getUser().getUserName())));

        this.historyProducer.send(getHistoryRequest(History.Operation.TRANSFERENCE, toAccount,
                String.format("Recebido Transferência de %s realizado por %s para %s",
                        transferRequest.getTransferedValue().toString(),
                        fromAccount.getUser().getUserName(), toAccount.getUser().getUserName())));
    }

    public void payBill(final Long userId, final BillRequest billRequest) {
        final Account account = this.accountService.findActiveByUserId(userId);
        account.withdraw(billRequest.getValue());

        this.accountService.save(account);
        this.historyProducer.send(getHistoryRequest(History.Operation.BILL_PAYMENT, account,
                String.format("Pagamento de Conta de %s realizado por %s",
                        Utils.getValueInBRLFormattedCurrency(billRequest.getValue()),
                        account.getUser().getUserName())));
    }

    private HistoryRequest getHistoryRequest(final History.Operation historyOperation, final Account account, final String message) {
        final HistoryRequest history = new HistoryRequest();
        history.setOperation(historyOperation.getOperation());
        history.setAccountId(account.getId());
        history.setCurrentBalance(account.getBalance());
        history.setMessage(message);
        return history;
    }
}
