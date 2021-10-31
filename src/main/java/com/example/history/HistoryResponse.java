package com.example.history;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class HistoryResponse implements Serializable {
    public static final long serialVersionUID = 1L;

    private String operation;
    private String message;
    private BigDecimal currentBalance;

    public HistoryResponse() {
    }

    public HistoryResponse(final String operation, final String message, final BigDecimal currentBalance) {
        this.operation = operation;
        this.message = message;
        this.currentBalance = currentBalance;
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

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryResponse that = (HistoryResponse) o;
        return Objects.equals(operation, that.operation) && Objects.equals(message, that.message) && Objects.equals(currentBalance, that.currentBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, message, currentBalance);
    }

    @Override
    public String toString() {
        return "HistoryResponse{" +
                "operation='" + operation + '\'' +
                ", message='" + message + '\'' +
                ", currentBalance=" + currentBalance +
                '}';
    }
}
