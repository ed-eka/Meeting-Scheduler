package com.metrodata.serverapp.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {
    private String code;
    private int status;

    public CustomException(String message, String code, int status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}