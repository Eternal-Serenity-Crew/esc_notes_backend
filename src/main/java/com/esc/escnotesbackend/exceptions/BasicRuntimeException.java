package com.esc.escnotesbackend.exceptions;

import lombok.Getter;

@Getter
public class BasicRuntimeException extends RuntimeException {
    private final String className;
    private final String methodName;

    public BasicRuntimeException(String message) {
        super(message);

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        this.className = stackTraceElements[3].getClassName();
        this.methodName = stackTraceElements[3].getMethodName();
    }

    public BasicRuntimeException(String message, Throwable cause) {
        super(message, cause);

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        this.className = stackTraceElements[3].getClassName();
        this.methodName = stackTraceElements[3].getMethodName();
    }

    @Override
    public String toString() {
        return "CustomException: " + getMessage() +
                " [Class: " + className +
                ", Method: " + methodName + "]";
    }
}
