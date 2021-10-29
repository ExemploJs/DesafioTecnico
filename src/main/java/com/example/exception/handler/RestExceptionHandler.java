package com.example.exception.handler;

import com.example.exception.AccountNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.exception.handler.response.HandlerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value
            = { UserNotFoundException.class, AccountNotFoundException.class })
    public ResponseEntity<?> handleNotFound(
            final RuntimeException e, final WebRequest request) {
        return new ResponseEntity<>(new HandlerResponse(HttpStatus.NOT_FOUND.value(),
                new Date(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobally(
            final Exception e, final WebRequest request) {
        final HandlerResponse errorDetail = new HandlerResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                e.getMessage());

        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
