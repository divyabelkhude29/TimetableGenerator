package com.timetable.exception;

public class DuplicateRecordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateRecordException() {
        super();
    }

    public DuplicateRecordException(String message) {
        super(message);
    }

    public DuplicateRecordException(String resourceName, String fieldName, String fieldValue) {
        super(resourceName + " already exists with " +
              fieldName + " : " + fieldValue);
    }

}