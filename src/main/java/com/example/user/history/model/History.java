package com.example.user.history.model;

import com.example.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class History implements Serializable {

    public enum Operation {
        WITHDRAW, DEPOSIT, TRANSFERENCE, BILL_PAYMENT;


        public static List<Operation> getAllValues() {
            return Arrays.stream(Operation.values()).collect(Collectors.toList());
        }
    }

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private Operation operation;

    private String message;

    @ManyToOne
    private User user;

    public History() {}

    public History(final Operation operation, final String message, final User user) {
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

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
