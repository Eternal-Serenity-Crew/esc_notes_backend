package com.esc.escnotesbackend.exceptions;

public class DoubleRecordException extends BasicRuntimeException {
    public DoubleRecordException(String message) {
        super(message);
    }

    public DoubleRecordException(String message, Throwable cause) {
        super(message, cause);
    }
}
