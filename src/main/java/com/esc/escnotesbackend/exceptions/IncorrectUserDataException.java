package com.esc.escnotesbackend.exceptions;

public class IncorrectUserDataException extends BasicRuntimeException {
    public IncorrectUserDataException(String message) {
        super(message);
    }

    public IncorrectUserDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
