package com.esc.escnotesbackend.exceptions.handlers;

import com.esc.escnotesbackend.exceptions.DoubleRecordException;
import com.esc.escnotesbackend.exceptions.ExecutionFailedException;
import com.esc.escnotesbackend.exceptions.IncorrectUserDataException;
import com.esc.escnotesbackend.exceptions.NoteNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorMessage> handleNullPointerException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage()) {
                });
    }

    @ExceptionHandler(DoubleRecordException.class)
    public ResponseEntity<ErrorMessage> handleDoubleRecordException(DoubleRecordException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(HttpStatus.CONFLICT, e.getMessage(), e.getClassName(), e.getMethodName(), e.getCause()) {
                });
    }

    @ExceptionHandler(IncorrectUserDataException.class)
    public ResponseEntity<ErrorMessage> handleIncorrectUserDataException(IncorrectUserDataException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage(), e.getClassName(), e.getMethodName(), e.getCause()) {
                });
    }

    @ExceptionHandler(ExecutionFailedException.class)
    public ResponseEntity<ErrorMessage> handleExecutionFailedException(ExecutionFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage(), e.getClassName(), e.getMethodName(), e.getCause()) {
                });
    }

    @ExceptionHandler(NoteNotExistException.class)
    public ResponseEntity<ErrorMessage> handleNoteNotExistException(NoteNotExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage(), e.getClassName(), e.getMethodName(), e.getCause()) {
                });
    }
}
