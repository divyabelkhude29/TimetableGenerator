package com.timetable.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with ID : " + id);
    }

    public ResourceNotFoundException(String resourceName,
                                     String fieldName,
                                     String fieldValue) {

        super(resourceName + " not found with " +
              fieldName + " : " + fieldValue);
    }

}