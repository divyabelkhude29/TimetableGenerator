package com.timetable.exception;

public class UnauthorizedAccessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedAccessException() {
        super();
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(String username, String resource) {
        super("User '" + username + "' is not authorized to access " + resource);
    }

}