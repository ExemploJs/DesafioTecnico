package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FieldCannotBeNullException extends RuntimeException {
    private static final String ERROR_MESSAGE_DEFAULT = "Campos não podem ser nulos!";

    public FieldCannotBeNullException(final String message) {
        super(message);
    }

    public FieldCannotBeNullException() {
        super(ERROR_MESSAGE_DEFAULT);
    }
}
