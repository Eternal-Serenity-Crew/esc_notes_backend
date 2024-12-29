package com.esc.escnotesbackend.exceptions.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ErrorMessage {
    private int httpCode;
    private String message;
    private String className;
    private String methodName;
    private Throwable cause;

    public ErrorMessage(HttpStatus httpCode, String message) {
        this.httpCode = httpCode.value();
        this.message = message;
    }

    public ErrorMessage(HttpStatus httpCode, String message, Throwable cause) {
        this.httpCode = httpCode.value();
        this.message = message;
        this.cause = cause;
    }
    public ErrorMessage(HttpStatus httpCode, String message, String className, String methodName, Throwable cause) {
        this.httpCode = httpCode.value();
        this.message = message;
        this.className = className;
        this.methodName = methodName;
        this.cause = cause;
    }
}
