package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FromUserIdCannotBeTheSameOfToUserIdException extends RuntimeException {

    private static final String ERROR_MESSAGE_DEFAULT = "O id de usuário de origem não pode ser o mesmo do usuário destino!";

    public FromUserIdCannotBeTheSameOfToUserIdException(final String message) {
        super(message);
    }

    public FromUserIdCannotBeTheSameOfToUserIdException() {
        super(ERROR_MESSAGE_DEFAULT);
    }
}
