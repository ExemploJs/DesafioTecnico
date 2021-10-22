package com.example.user.history.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class History implements Serializable {

    public enum Operation {
        WITHDRAW, DEPOSIT, TRANSFERENCE, BILL_PAYMENT
    }

    @Id
    @GeneratedValue
    private Long id;

    private Operation operation;

    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
