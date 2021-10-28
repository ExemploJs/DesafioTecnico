package com.example.user.history.request;

import java.io.Serializable;

public class HistoryRequest implements Serializable {

    private String operation;
    private String message;
    private long accountId;

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
}
