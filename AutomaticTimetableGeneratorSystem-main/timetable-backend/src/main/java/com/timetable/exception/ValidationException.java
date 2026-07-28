package com.timetable.exception;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String fieldName, String message) {
        super("Validation failed for " + fieldName + " : " + message);
    }

}