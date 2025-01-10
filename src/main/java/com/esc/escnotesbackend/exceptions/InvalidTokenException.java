package com.esc.escnotesbackend.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BasicRuntimeException {
    public InvalidTokenException() {
        super("Invalid token received", HttpStatus.NOT_FOUND);
    }

    public InvalidTokenException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
