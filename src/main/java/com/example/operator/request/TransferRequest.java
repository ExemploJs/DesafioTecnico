package com.example.operator.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class TransferRequest implements Serializable {

    public static final long serialVersionUID = 1L;
    private String message;
    private BigDecimal transferedValue;

    public TransferRequest() {
    }

    public TransferRequest(final String message, final BigDecimal transferedValue) {
        this.message = message;
        this.transferedValue = transferedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public BigDecimal getTransferedValue() {
        return transferedValue;
    }

    public void setTransferedValue(final BigDecimal transferedValue) {
        this.transferedValue = transferedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferRequest that = (TransferRequest) o;
        return Objects.equals(message, that.message) && Objects.equals(transferedValue, that.transferedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, transferedValue);
    }
}
