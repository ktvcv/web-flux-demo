package com.example.webfluxdemo.exceptions;

public class AuthException extends ApiException{

    public AuthException(final String message, final String errorCode) {
        super(message, errorCode);
    }
}
