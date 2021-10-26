package com.example.user.history.model;

import com.example.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class History implements Serializable {

    public enum Operation {
        WITHDRAW, DEPOSIT, TRANSFERENCE, BILL_PAYMENT
    }

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private Operation operation;

    private String message;

    @ManyToOne
    private User user;

    public History() {
    }

    public History(Operation operation, String message, User user) {
        this.operation = operation;
        this.message = message;
        this.user = user;
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

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
