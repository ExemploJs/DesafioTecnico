package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccountDoesntHaveBalanceException extends RuntimeException {

    private static final String ERROR_MESSAGE_DEFAULT = "A conta não tem saldo disponível!";

    public AccountDoesntHaveBalanceException(final String message) {
        super(message);
    }

    public AccountDoesntHaveBalanceException() {
        super(ERROR_MESSAGE_DEFAULT);
    }
}
