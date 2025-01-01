package com.esc.escnotesbackend.exceptions;

public class IncorrectTokenException extends RuntimeException {
    public IncorrectTokenException() {
        super("Incorrect token");
    }

    public IncorrectTokenException(String message) {
        super(message);
    }

    public IncorrectTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
