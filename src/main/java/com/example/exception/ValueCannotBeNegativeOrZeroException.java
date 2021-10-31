package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValueCannotBeNegativeOrZeroException extends RuntimeException {
    private static final String ERROR_MESSAGE_DEFAULT = "O valor não pode ser zero ou negativo!";

    public ValueCannotBeNegativeOrZeroException(final String message) {
        super(message);
    }

    public ValueCannotBeNegativeOrZeroException() {
        super(ERROR_MESSAGE_DEFAULT);
    }
}
