package com.example.user.history.model.history.request;

import com.example.user.history.model.History;

public class HistoryRequest {

    public enum Operation {
        WITHDRAW, DEPOSIT, TRANSFERENCE, BILL_PAYMENT
    }

    private String operation;

    private String message;

    private long userId;

    public HistoryRequest() {
    }

    public HistoryRequest(final String operation, String message, long userId) {
        this.operation = operation;
        this.message = message;
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
