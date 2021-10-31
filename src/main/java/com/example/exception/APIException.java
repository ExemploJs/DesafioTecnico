package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class APIException extends RuntimeException {

    private static final String ERROR_MESSAGE_DEFAULT = "Ocorreu um erro interno no servidor! Contate o Administrador ou tente novamente mais tarde!";

    public APIException(final String message) {
        super(message);
    }

    public APIException() {
        super(ERROR_MESSAGE_DEFAULT);
    }
}
