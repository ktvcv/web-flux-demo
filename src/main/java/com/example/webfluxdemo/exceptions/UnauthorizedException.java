package com.example.webfluxdemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends ApiException {

    public UnauthorizedException(final String message) {
        super(message, "UNAUTHORIZED");
    }
}
