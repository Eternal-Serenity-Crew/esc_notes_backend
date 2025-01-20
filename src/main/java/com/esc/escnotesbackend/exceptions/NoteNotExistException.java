package com.esc.escnotesbackend.exceptions;

public class NoteNotExistException extends BasicRuntimeException {
    public NoteNotExistException(String message) {
        super(message);
    }
}
