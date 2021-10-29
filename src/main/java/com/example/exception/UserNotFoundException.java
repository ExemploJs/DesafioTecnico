package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE_DEFAULT = "Usuário não encontrado/cadastrado!";
    private String message;

    public UserNotFoundException(final Throwable cause, final String message) {
        super(message, cause);
        this.message = message;
    }

    public UserNotFoundException(final String message) {
        super(message);
        this.message = message;
    }

    public UserNotFoundException() {
        super(ERROR_MESSAGE_DEFAULT);
    }

    public String getMessage() {
        return this.message;
    }
}
