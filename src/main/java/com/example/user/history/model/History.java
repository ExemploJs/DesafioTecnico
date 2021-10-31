package com.example.user.history.model;

import com.example.user.account.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class History implements Serializable {

    public enum Operation {
        WITHDRAW("Saque"), DEPOSIT("Depósito"), TRANSFERENCE("Transferência"), BILL_PAYMENT("Pagamento de Contas");


        private final String operation;

        Operation(final String op) {
            this.operation = op;
        }

        public static List<Operation> getAllValues() {
            return Arrays.stream(Operation.values()).collect(Collectors.toList());
        }

        public String getOperation() {
            return this.operation;
        }
    }

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private Operation operation;

    private String message;

    @ManyToOne
    private Account account;

    private BigDecimal currentBalance;

    public History() {}

    public History(final Operation operation, final String message, final Account account, final BigDecimal currentBalance) {
        this.operation = operation;
        this.message = message;
        this.account = account;
        this.currentBalance = currentBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(final Operation operation) {
        this.operation = operation;
    }

    public void setOperation(final String operationName) {
        Operation.getAllValues().stream()
                .filter(name -> name.toString().equals(operationName))
                .findAny()
                .ifPresent(this::setOperation);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
}
