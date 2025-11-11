package com.companyz.ems.exception;

/**
 * Exception thrown when a requested entity is not found in the database.
 * This includes employee not found, pay statement not found, etc.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class NotFoundException extends EMSException {

    /**
     * Constructs a new NotFoundException with the specified detail message.
     * 
     * @param message the detail message describing what was not found
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new NotFoundException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new NotFoundException with the specified cause.
     * 
     * @param cause the cause of the exception
     */
    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
