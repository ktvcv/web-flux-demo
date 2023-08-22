package com.example.webfluxdemo.exceptions;

public class ApiException extends RuntimeException{

    protected String errorCode;

    public ApiException(final String message, final String errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
