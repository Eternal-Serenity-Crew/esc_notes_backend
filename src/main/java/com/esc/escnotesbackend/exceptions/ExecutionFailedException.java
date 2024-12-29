package com.esc.escnotesbackend.exceptions;

public class ExecutionFailedException extends BasicRuntimeException {
    public ExecutionFailedException(String message) {
        super(message);
    }

    public ExecutionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
