package com.example.history.request;

import com.example.history.entity.History;

import java.io.Serializable;
import java.math.BigDecimal;

public class HistoryRequest implements Serializable {

    public static final long serialVersionUID = 1L;
    private History.Operation operation;
    private String message;
    private long accountId;
    private BigDecimal currentBalance;

    public HistoryRequest() {
    }

    public History.Operation getOperation() {
        return operation;
    }

    public void setOperation(final History.Operation operation) {
        this.operation = operation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
}
