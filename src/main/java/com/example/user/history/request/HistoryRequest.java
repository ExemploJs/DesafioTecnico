package com.example.user.history.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class HistoryRequest implements Serializable {

    public static final long serialVersionUID = 1L;
    private String operation;
    private String message;
    private long accountId;
    private BigDecimal currentBalance;

    public HistoryRequest() {
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
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
