package com.companyz.ems.exception;

/**
 * Exception thrown when input validation fails.
 * This includes invalid SSN format, negative salaries, empty required fields, etc.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class ValidationException extends EMSException {

    /**
     * Constructs a new ValidationException with the specified detail message.
     * 
     * @param message the detail message describing the validation failure
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new ValidationException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new ValidationException with the specified cause.
     * 
     * @param cause the cause of the exception
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
