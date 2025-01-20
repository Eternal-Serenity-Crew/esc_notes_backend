package com.esc.escnotesbackend.exceptions;

public class NoteSectionNotExistException extends BasicRuntimeException {
    public NoteSectionNotExistException() {
        super("Note section with this Id does not exist");
    }

    public NoteSectionNotExistException(String message) {
        super(message);
    }
}
