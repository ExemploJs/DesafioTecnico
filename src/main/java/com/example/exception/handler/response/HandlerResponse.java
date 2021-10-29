package com.example.exception.handler.response;

import java.io.Serializable;

public class HandlerResponse implements Serializable {

    public static final long serialVersionUID = 1L;

    private int code;
    private String message;

    public HandlerResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
