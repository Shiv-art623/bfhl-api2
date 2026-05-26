package com.campus.bfhl.exception;

/**
 * Custom runtime exception thrown when the input validation fails.
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
