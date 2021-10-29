package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountException extends RuntimeException {

    private static final String ERROR_MESSAGE_DEFAULT = "Conta não encontrada ou não cadastrada!";
    private String message;

    public AccountException(final Throwable cause, final String message) {
        super(message, cause);
    }

    public AccountException(final String message) {
        super(message);
    }

    public AccountException() {
        super(ERROR_MESSAGE_DEFAULT);
    }

    public String getMessage() {
        return this.message;
    }
}
