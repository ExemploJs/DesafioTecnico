package com.example.exception.handler.response;

import java.io.Serializable;
import java.util.Date;

public class HandlerResponse implements Serializable {

    public static final long serialVersionUID = 1L;

    private int code;
    private Date timestamp;
    private String message;

    public HandlerResponse(){}

    public HandlerResponse(int code, Date timestamp, String message) {
        this.code = code;
        this.timestamp = timestamp;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
