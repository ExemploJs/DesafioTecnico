package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE_DEFAULT = "Conta não encontrada ou não cadastrada!";

    public AccountNotFoundException(final String message) {
        super(message);
    }

    public AccountNotFoundException() {
        super(ERROR_MESSAGE_DEFAULT);
    }
}
